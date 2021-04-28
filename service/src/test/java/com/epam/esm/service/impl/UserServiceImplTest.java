package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.entity.User;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrdersBuilder;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UserDto;
import com.epam.esm.service.exception.EmptyOrderException;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.InsufficientFundInAccount;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.ServiceException;
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
class UserServiceImplTest {
  @Mock private UserRepository userRepository;
  @Mock private OrdersRepository ordersRepository;
  @Mock private GiftCertificateRepository giftCertificateRepository;
  @Mock private UserDtoBuilder userDtoBuilder;
  @Mock private OrdersBuilder ordersBuilder;
  @Mock private GiftCertificateDtoBuilder giftCertificateDtoBuilder;
  @Mock private OrdersDtoBuilder ordersDtoBuilder;
  @Mock private TagDtoBuilder tagDtoBuilder;
  @Mock private TagRepository tagRepository;
  @Mock private PageValidator pageValidator;

  @InjectMocks UserServiceImpl userService;

  private static final long USER_ID = 1L;
  private static final String LOGIN = "Valid Login";
  private static final String PASSWORD = "Valid Password";
  private static final BigDecimal ACCOUNT = new BigDecimal("100");
  private User user = new User(USER_ID, LOGIN, PASSWORD, ACCOUNT);
  private List<User> users = new ArrayList<>();
  private List<UserDto> userDtos = new ArrayList<>();
  private static final int DEFAULT_PAGE_PARAMETER = 1;
  private static final int DEFAULT_SIZE_PARAMETER = 1;
  private static final long FIRST_ORDER_ID = 1L;
  private static final BigDecimal FIRST_ORDER_COST = BigDecimal.valueOf(10);
  private static final LocalDateTime FIRST_ORDER_CREATE_TIME = LocalDateTime.now();
  private Orders order = new Orders(FIRST_ORDER_ID, FIRST_ORDER_COST, FIRST_ORDER_CREATE_TIME);
  private Set<TagDto> emptyTagSet = new HashSet<>();
  private GiftCertificate giftCertificate =
      new GiftCertificate(
          1L,
          "anotherValidName1",
          "validDescription1",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now());
  private GiftCertificateDto giftCertificateDto =
      new GiftCertificateDto(
          1L,
          "anotherValidName1",
          "validDescription1",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now(),
          emptyTagSet);

  private List<GiftCertificateDto> expectedGiftCertificateDtos =
      Collections.singletonList(giftCertificateDto);
  private OrdersDto orderDto =
      new OrdersDto(
          FIRST_ORDER_ID,
          FIRST_ORDER_COST,
          FIRST_ORDER_CREATE_TIME,
          USER_ID,
          expectedGiftCertificateDtos);
  private UserDto userDto = new UserDto(USER_ID, LOGIN, ACCOUNT, Set.of(orderDto));
  private List<Long> giftCertificateIds = Collections.singletonList(1L);
  private static final long MOST_WIDE_TAG_ID = 2L;
  private static final String MOST_WIDE_TAG_NAME = "The Most";
  private Tag mostWideTag = new Tag(MOST_WIDE_TAG_ID, MOST_WIDE_TAG_NAME);
  private TagDto expectedMostWideTag = new TagDto(MOST_WIDE_TAG_ID, MOST_WIDE_TAG_NAME);

  @BeforeEach
  void setUp() {
    users.add(user);
    userDtos.add(userDto);
  }

  @Test
  void testGetAll_shouldReturnUsers_whenTheyAreExistInDatabase() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    when(userRepository.getEntityListWithPaginationBySpecification(any(), anyInt(), anyInt()))
        .thenReturn(users);
    when(userDtoBuilder.build(user)).thenReturn(userDto);
    // when
    List<UserDto> actualUserDtos =
        userService.getAll(DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER);
    // then
    assertEquals(userDtos, actualUserDtos);
  }

  @Test
  void testGetAll_shouldReturnThrowPageNotValidException_whenPageParametersNotTransmitted() {
    // given
    when(pageValidator.isValid(any())).thenReturn(false);
    // when
    // then
    assertThrows(PageNotValidException.class, () -> userService.getAll(null, null));
  }

  @Test
  void testMakeOrder_shouldThrowEmptyOrderException_whenOrderIsNull() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    // when
    // then
    assertThrows(EmptyOrderException.class, () -> userService.makeOrder(USER_ID, null));
  }

  @Test
  void testMakeOrder_shouldThrowEmptyOrderException_whenOrderIsEmpty() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    // when
    // then
    assertThrows(
        EmptyOrderException.class, () -> userService.makeOrder(USER_ID, new ArrayList<>()));
  }

  @Test
  void testMakeOrder_shouldThrowInsufficientFundInAccount_whenUserAccountLessThanOrderCost() {
    // given
    User poorUser = user;
    poorUser.setAccount(BigDecimal.valueOf(0));
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.of(poorUser));
    when(giftCertificateRepository.getEntityBySpecification(any()))
        .thenReturn(Optional.ofNullable(giftCertificate));
    // when
    // then
    assertThrows(
        InsufficientFundInAccount.class, () -> userService.makeOrder(USER_ID, giftCertificateIds));
  }

  @Test
  void testMakeOrder_shouldThrowEntityNotFoundException_whenUserWasNotUpdated() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    // when
    // then
    assertThrows(
        EntityNotFoundException.class, () -> userService.makeOrder(USER_ID, giftCertificateIds));
  }

  @Test
  void testMakeOrder_shouldThrowEntityNotFoundException_whenUserNotUpdated() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    when(giftCertificateRepository.getEntityBySpecification(any()))
        .thenReturn(Optional.ofNullable(giftCertificate));
    // when
    // then
    assertThrows(
        EntityNotFoundException.class, () -> userService.makeOrder(USER_ID, giftCertificateIds));
  }

  @Test
  void testMakeOrder_shouldThrowServiceException_whenOrdersWasNotSaved() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    when(userDtoBuilder.build(any())).thenReturn(userDto);
    when(giftCertificateRepository.getEntityBySpecification(any()))
        .thenReturn(Optional.ofNullable(giftCertificate));
    when(userRepository.update(user)).thenReturn(Optional.ofNullable(user));
    // when
    // then
    assertThrows(ServiceException.class, () -> userService.makeOrder(USER_ID, giftCertificateIds));
  }

  @Test
  void testMakeOrder_shouldOrder_whenUserMadeOrderSuccessful() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.ofNullable(user));
    when(userDtoBuilder.build(any())).thenReturn(userDto);
    when(giftCertificateRepository.getEntityBySpecification(any()))
        .thenReturn(Optional.ofNullable(giftCertificate));
    when(userRepository.update(user)).thenReturn(Optional.ofNullable(user));
    when(ordersRepository.save(any())).thenReturn(Optional.ofNullable(order));
    when(ordersDtoBuilder.build(any())).thenReturn(orderDto);
    // when
    Optional<OrdersDto> actualOptionalOrdersDto =
        userService.makeOrder(USER_ID, giftCertificateIds);
    // then
    assertEquals(Optional.of(orderDto), actualOptionalOrdersDto);
  }

  @Test
  void testGetById_shouldReturnUser_whenUserIsExistsInDataBase() {
    // given
    when(userRepository.getEntityBySpecification(any())).thenReturn(Optional.of(user));
    when(userDtoBuilder.build(any())).thenReturn(userDto);
    // when
    UserDto actualUserDto = userService.getById(USER_ID).orElse(new UserDto());
    // then
    assertEquals(userDto, actualUserDto);
  }

  @Test
  void testGetMostWidelyUsedTag_shouldReturnTag_whenItIsExists() {
    // given
    when(tagRepository.getEntityListBySpecification(any()))
        .thenReturn(Collections.singletonList(mostWideTag));
    when(tagDtoBuilder.build(mostWideTag)).thenReturn(expectedMostWideTag);
    // when
    Optional<TagDto> actualOptionalTag = userService.getMostWidelyUsedTagByUserId(USER_ID);
    // then
    assertEquals(Optional.of(expectedMostWideTag), actualOptionalTag);
  }
}
