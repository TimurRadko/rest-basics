package com.epam.esm.link.builder;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UsersDto;
import com.epam.esm.controller.UsersController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDtoLinkBuilder implements LinkBuilder<UsersDto> {
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
  public UsersDto build(UsersDto usersDto) {
    addLinkToOrderDtos(usersDto);
    return usersDto.add(linkTo(methodOn(UsersController.class).get(usersDto.getId())).withSelfRel());
  }

  private void addLinkToOrderDtos(UsersDto usersDto) {
    usersDto.setOrders(
        usersDto.getOrders().stream()
            .map(orderDto -> addLinkToOrderDto(orderDto, usersDto))
            .collect(Collectors.toSet()));
  }

  private OrdersDto addLinkToOrderDto(OrdersDto ordersDto, UsersDto usersDto) {
    addLinkToGiftCertificateDtos(ordersDto);
    return ordersDto.add(
        linkTo(methodOn(UsersController.class).getOrdersById(usersDto.getId(), ordersDto.getId()))
            .withSelfRel());
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

  public OrdersDto addLinkToOrderDtoUsingUserId(OrdersDto ordersDto, long userId) {
    addLinkToGiftCertificateDtos(ordersDto);
    return ordersDto.add(
        linkTo(methodOn(UsersController.class).getOrdersById(userId, ordersDto.getId()))
            .withSelfRel());
  }
}
