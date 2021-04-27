package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.OrdersGiftCertificate;

import java.util.Optional;

/**
 * * This interface describes a common operations with OrdersGiftCertificate's Entities situated in
 * the DB
 */
public interface OrdersGiftCertificateRepository extends Repository<OrdersGiftCertificate> {
  /**
   * * This method describes a general save (create) operation for all OrdersGiftCertificate's,
   * located in the DB
   *
   * @param ordersGiftCertificate - OrdersGiftCertificate, which transmitted in the method as a args
   * @return Optional<OrdersGiftCertificate> - container that is contained OrdersGiftCertificate
   */
  Optional<OrdersGiftCertificate> save(OrdersGiftCertificate ordersGiftCertificate);
}
