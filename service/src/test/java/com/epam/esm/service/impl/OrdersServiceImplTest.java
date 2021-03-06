package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.user.UserDoesNotHaveOrderException;
import com.epam.esm.service.locale.LocaleTranslator;
import com.epam.esm.service.validator.PageValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdersServiceImplTest {
  @Mock private OrdersRepository ordersRepository;
  @Mock private OrdersDtoBuilder ordersDtoBuilder;
  @Mock private PageValidator pageValidator;
  @Mock
  LocaleTranslator localeTranslator;

  @InjectMocks private OrdersServiceImpl ordersService;

  private static final int DEFAULT_PAGE_PARAMETER = 1;
  private static final int DEFAULT_SIZE_PARAMETER = 1;
  private static final long DEFAULT_USER_ID = 1L;
  private static final long FIRST_ORDER_ID = 1L;
  private static final BigDecimal FIRST_ORDER_COST = new BigDecimal("10");
  private static final LocalDateTime FIRST_ORDER_CREATE_TIME = LocalDateTime.now();
  private final Orders order =
      new Orders(FIRST_ORDER_ID, FIRST_ORDER_COST, FIRST_ORDER_CREATE_TIME);
  private final Set<TagDto> emptyTagSet = new HashSet<>();
  private final GiftCertificateDto giftCertificateDto =
      new GiftCertificateDto(
          1L,
          "anotherValidName1",
          "validDescription1",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now(),
          emptyTagSet);

  private final List<GiftCertificateDto> expectedGiftCertificateDtos =
      Collections.singletonList(giftCertificateDto);
  private final OrdersDto orderDto =
      new OrdersDto(
          FIRST_ORDER_ID,
          FIRST_ORDER_COST,
          FIRST_ORDER_CREATE_TIME,
          DEFAULT_USER_ID,
          expectedGiftCertificateDtos);
  private final List<OrdersDto> expectedOrderDtos = new ArrayList<>();

  @BeforeEach
  void setUp() {
    expectedOrderDtos.add(orderDto);
  }

  @Test
  void testGetAllOrdersByUserId_shouldReturnAllOrders_whenItIsExists() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    when(ordersRepository.getEntityListWithPagination(any(), anyInt(), anyInt()))
        .thenReturn(Collections.singletonList(order));
    when(ordersDtoBuilder.build(order)).thenReturn(orderDto);
    // when
    List<OrdersDto> actualOrders =
        ordersService.getAllOrdersByUserId(
            DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, DEFAULT_USER_ID);
    // then
    assertEquals(expectedOrderDtos, actualOrders);
  }

  @Test
  void testGetAllOrdersByUserId_shouldThrowPageNotValidException_whenPageParametersNotValid() {
    // given
    when(pageValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(
        PageNotValidException.class,
        () ->
            ordersService.getAllOrdersByUserId(
                DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER, DEFAULT_USER_ID));
  }

  @Test
  void testGetById_shouldReturnOrders_whenItExists() {
    // given
    when(ordersRepository.getEntity(any())).thenReturn(Optional.of(order));
    when(ordersDtoBuilder.build(order)).thenReturn(orderDto);
    // when
    Optional<OrdersDto> actualOptionalOrdersDto = ordersService.getById(DEFAULT_USER_ID);
    // then
    assertEquals(Optional.of(orderDto), actualOptionalOrdersDto);
  }

  @Test
  void testGetByUserAndOrderId_shouldReturnCorrectOrders_whenItExists() {
    // given
    when(ordersRepository.getEntityList(any())).thenReturn(Collections.singletonList(order));
    when(ordersRepository.getEntity(any())).thenReturn(Optional.of(order));
    when(ordersDtoBuilder.build(order)).thenReturn(orderDto);
    // when
    Optional<OrdersDto> actualOptionalOrdersDto =
        ordersService.getByUserAndOrderId(DEFAULT_USER_ID, FIRST_ORDER_ID);
    // then
    assertEquals(Optional.of(orderDto), actualOptionalOrdersDto);
  }

  @Test
  void testGetByUserAndOrderId_shouldThrowEmptyOrderException_whenItDoesNotExist() {
    // given
    when(ordersRepository.getEntityList(any())).thenReturn(new ArrayList<>());
    when(ordersRepository.getEntity(any())).thenReturn(Optional.of(order));
    // when
    when(localeTranslator.toLocale(any()))
        .thenReturn(
            String.format(
                "User with id = %s doesn't have order with id = %s.",
                DEFAULT_USER_ID, FIRST_ORDER_ID));
    // then
    assertThrows(
        UserDoesNotHaveOrderException.class,
        () -> ordersService.getByUserAndOrderId(DEFAULT_USER_ID, FIRST_ORDER_ID));
  }
}
