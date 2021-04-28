package com.epam.esm.web.link.builder;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.web.controller.UsersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDtoLinkBuilder implements LinkBuilder<UserDto> {
  private final LinkBuilder<GiftCertificateDto> giftCertificateDtoLinkBuilder;
  private final LinkBuilder<TagDto> tagDtoLinkBuilder;

  @Autowired
  public UserDtoLinkBuilder(
      LinkBuilder<GiftCertificateDto> giftCertificateDtoLinkBuilder,
      LinkBuilder<TagDto> tagDtoLinkBuilder) {
    this.giftCertificateDtoLinkBuilder = giftCertificateDtoLinkBuilder;
    this.tagDtoLinkBuilder = tagDtoLinkBuilder;
  }

  @Override
  public UserDto build(UserDto userDto) {
    addLinkToOrderDtos(userDto);
    return userDto.add(linkTo(methodOn(UsersController.class).get(userDto.getId())).withSelfRel());
  }

  private void addLinkToOrderDtos(UserDto userDto) {
    userDto.setOrders(
        userDto.getOrders().stream()
            .map(orderDto -> addLinkToOrderDto(orderDto, userDto))
            .collect(Collectors.toSet()));
  }

  private OrdersDto addLinkToOrderDto(OrdersDto ordersDto, UserDto userDto) {
    addLinkToGiftCertificateDtos(ordersDto);
    return ordersDto.add(
        linkTo(methodOn(UsersController.class).get(userDto.getId())).withSelfRel());
  }

  public OrdersDto addLinkToGiftCertificateDtos(OrdersDto ordersDto) {
    ordersDto.setGiftCertificates(
        ordersDto.getGiftCertificates().stream()
            .map(giftCertificateDtoLinkBuilder::build)
            .collect(Collectors.toList()));
    return ordersDto;
  }

  public TagDto addLinkMostWidelyUsedTag(TagDto tagDto) {
    return tagDtoLinkBuilder.build(tagDto);
  }

  public OrdersDto addLinkToOrderDtoUsingUserId(
      OrdersDto ordersDto, Integer page, Integer size, long userId) {
    addLinkToGiftCertificateDtos(ordersDto);
    return ordersDto.add(
        linkTo(methodOn(UsersController.class).getOrdersByUserId(page, size, userId))
            .withSelfRel());
  }
}
