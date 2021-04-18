package com.epam.esm.web.controller;

import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/users")
public class UsersController {
  private UserService userService;
  private OrderService orderService;

  @Autowired
  public UsersController(UserService userService, OrderService orderService) {
    this.userService = userService;
    this.orderService = orderService;
  }

  @GetMapping()
  public List<UserDto> getAll() {
    return userService.getAll();
  }

  @GetMapping("/{id}")
  public UserDto getById(@PathVariable Long id) {
    return userService
        .getById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
  }

  @GetMapping("/{id}/orders")
  public List<OrderDto> getOrdersByUserId(@PathVariable Long id) {
    return orderService.getAllOrdersByUserId(id);
  }

  @PostMapping("/{id}/certificates")
  public UserDto save(
      @PathVariable Long id,
      @RequestBody List<GiftCertificateDto> giftCertificateDtos,
      HttpServletRequest request,
      HttpServletResponse response) {
    Optional<UserDto> optionalMadeOrder = userService.makeOrder(id, giftCertificateDtos);
    UserDto userDto =
        optionalMadeOrder.orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));

    String url = request.getRequestURL().toString();

    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
    return userDto;
  }
}
