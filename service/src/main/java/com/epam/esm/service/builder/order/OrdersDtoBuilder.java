package com.epam.esm.service.builder.order;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.dto.OrdersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrdersDtoBuilder {
  private final GiftCertificateDtoBuilder giftCertificateDtoBuilder;

  @Autowired
  public OrdersDtoBuilder(GiftCertificateDtoBuilder giftCertificateDtoBuilder) {
    this.giftCertificateDtoBuilder = giftCertificateDtoBuilder;
  }

  public OrdersDto build(Orders orders) {
    OrdersDto ordersDto = new OrdersDto();
    ordersDto.setId(orders.getId());
    ordersDto.setUserId(orders.getUser().getId());
    ordersDto.setCost(orders.getCost());
    ordersDto.setOrderDate(orders.getOrderDate());
    ordersDto.setGiftCertificates(
        orders.getGiftCertificates().stream()
            .map(giftCertificateDtoBuilder::build)
            .collect(Collectors.toList()));
    return ordersDto;
  }
}
