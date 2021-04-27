package com.epam.esm.web.link.builder;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrderDto;
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

  private OrderDto addLinkToOrderDto(OrderDto orderDto, UserDto userDto) {
    addLinkToGiftCertificateDtos(orderDto);
    return orderDto.add(
        linkTo(methodOn(UsersController.class).get(userDto.getId())).withSelfRel());
  }

  public OrderDto addLinkToGiftCertificateDtos(OrderDto orderDto) {
    orderDto.setGiftCertificates(
        orderDto.getGiftCertificates().stream()
            .map(giftCertificateDtoLinkBuilder::build)
            .collect(Collectors.toSet()));
    return orderDto;
  }

  public TagDto addLinkMostWidelyUsedTag(TagDto tagDto) {
    return tagDtoLinkBuilder.build(tagDto);
  }
}
