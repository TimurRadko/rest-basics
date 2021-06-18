package com.epam.esm.service.builder.order;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.Users;
import com.epam.esm.service.builder.certificate.GiftCertificateBuilder;
import com.epam.esm.service.dto.OrdersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrdersBuilder {
  private final GiftCertificateBuilder giftCertificateBuilder;

  @Autowired
  public OrdersBuilder(GiftCertificateBuilder giftCertificateBuilder) {
    this.giftCertificateBuilder = giftCertificateBuilder;
  }

  public Orders build(OrdersDto ordersDto, Users users) {
    Orders orders = new Orders();
    orders.setId(ordersDto.getId());
    orders.setUser(users);
    orders.setCost(ordersDto.getCost());
    orders.setOrderDate(ordersDto.getOrderDate());
    orders.setGiftCertificates(
        ordersDto.getGiftCertificates().stream()
            .map(giftCertificateBuilder::build)
            .collect(Collectors.toList()));
    return orders;
  }
}
