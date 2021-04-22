package com.epam.esm.service.builder.order;

import com.epam.esm.dao.entity.Order;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderBuilder {
  private final GiftCertificateBuilder giftCertificateBuilder;

  @Autowired
  public OrderBuilder(GiftCertificateBuilder giftCertificateBuilder) {
    this.giftCertificateBuilder = giftCertificateBuilder;
  }

  public Order build(OrderDto orderDto) {
    Order order = new Order();
    order.setId(orderDto.getId());
    order.setCost(orderDto.getCost());
    order.setOrderDate(orderDto.getOrderDate());
    order.setGiftCertificates(
        orderDto.getGiftCertificates().stream()
            .map(giftCertificateBuilder::build)
            .collect(Collectors.toSet()));
    return order;
  }
}
