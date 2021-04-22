package com.epam.esm.service.builder.order;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderDtoBuilder {
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;

  @Autowired
  public OrderDtoBuilder(GiftCertificateDtoBuilder giftCertificateDtoBuilder) {
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
  }

  public OrderDto build(Order order) {
    OrderDto orderDto = new OrderDto();
    orderDto.setId(order.getId());
    orderDto.setCost(order.getCost());
    orderDto.setOrderDate(order.getOrderDate());
    orderDto.setGiftCertificates(
        order.getGiftCertificates().stream()
            .map(giftCertificateDtoBuilder::build)
            .collect(Collectors.toSet()));
    return orderDto;
  }
}
