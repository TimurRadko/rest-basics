package com.epam.esm.controller;

import com.epam.esm.link.builder.UserDtoLinkBuilder;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.GiftCertificateDtoIds;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UsersDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/users")
public class UsersController {
  private final UserService userService;
  private final OrderService orderService;
  private final UserDtoLinkBuilder userDtoLinkBuilder;

  @Autowired
  public UsersController(
      UserService userService, OrderService orderService, UserDtoLinkBuilder userDtoLinkBuilder) {
    this.userService = userService;
    this.orderService = orderService;
    this.userDtoLinkBuilder = userDtoLinkBuilder;
  }

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public List<UsersDto> getAll(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size) {
    return userService.getAll(page, size).stream()
        .map(userDtoLinkBuilder::build)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public UsersDto get(@PathVariable Long id) {
    return userDtoLinkBuilder.build(
        userService
            .getById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")")));
  }

  @GetMapping("/{id}/orders")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public List<OrdersDto> getOrdersByUserId(
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "size", required = false) Integer size,
      @PathVariable Long id) {
    return orderService.getAllOrdersByUserId(page, size, id).stream()
        .map(ordersDto -> userDtoLinkBuilder.addLinkToOrderDtoUsingUserId(ordersDto, id))
        .collect(Collectors.toList());
  }

  @PostMapping("/{id}/orders")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public OrdersDto makeOrder(
      @PathVariable Long id,
      @RequestBody GiftCertificateDtoIds giftCertificateDtoIds,
      HttpServletRequest request,
      HttpServletResponse response) {
    Optional<OrdersDto> optionalOrderDto = userService.makeOrder(id, giftCertificateDtoIds);
    OrdersDto ordersDto =
        optionalOrderDto.orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    String url = request.getRequestURL().toString();

    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
    return userDtoLinkBuilder.addLinkToGiftCertificateDtos(ordersDto);
  }

  @GetMapping("/{userId}/orders/{orderId}")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public OrdersDto getOrdersById(@PathVariable Long userId, @PathVariable Long orderId) {
    Optional<OrdersDto> optionalOrdersDto = orderService.getByUserAndOrderId(userId, orderId);
    OrdersDto ordersDto =
        optionalOrdersDto.orElseThrow(
            () ->
                new EntityNotFoundException("Requested resource not found (id = " + orderId + ")"));
    return userDtoLinkBuilder.addLinkToOrderDtoUsingUserId(ordersDto, userId);
  }

  @GetMapping("/{id}/tags")
  @PreAuthorize("hasAnyAuthority('ROLE_USER', 'USER_ADMIN')")
  public TagDto getMostWidelyUsedTag(@PathVariable Long id) {
    return userDtoLinkBuilder.addLinkMostWidelyUsedTag(
        userService
            .getMostWidelyUsedTagByUserId(id)
            .orElseThrow(() -> new EntityNotFoundException("The most widely tag wasn't searched")));
  }
}