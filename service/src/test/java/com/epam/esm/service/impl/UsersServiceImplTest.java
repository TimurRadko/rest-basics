package com.epam.esm.service.impl;

import com.epam.esm.dao.entity.GiftCertificate;
import com.epam.esm.dao.entity.Orders;
import com.epam.esm.dao.entity.Role;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.entity.Users;
import com.epam.esm.dao.repository.GiftCertificateRepository;
import com.epam.esm.dao.repository.OrdersRepository;
import com.epam.esm.dao.repository.TagRepository;
import com.epam.esm.dao.repository.UserRepository;
import com.epam.esm.service.builder.certificate.GiftCertificateDtoBuilder;
import com.epam.esm.service.builder.order.OrdersBuilder;
import com.epam.esm.service.builder.order.OrdersDtoBuilder;
import com.epam.esm.service.builder.tag.TagDtoBuilder;
import com.epam.esm.service.builder.user.UserBuilder;
import com.epam.esm.service.builder.user.UserDetailsBuilder;
import com.epam.esm.service.builder.user.UserDtoBuilder;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.GiftCertificateDtoIds;
import com.epam.esm.service.dto.OrdersDto;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.UsersCreatingDto;
import com.epam.esm.service.dto.UsersDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.PageNotValidException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.order.EmptyOrderException;
import com.epam.esm.service.exception.order.InsufficientFundInAccount;
import com.epam.esm.service.exception.user.UserLoginExistsException;
import com.epam.esm.service.locale.TranslatorLocale;
import com.epam.esm.service.validator.PageValidator;
import com.epam.esm.service.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
class UsersServiceImplTest {
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
  @Mock private UserValidator userValidator;
  @Mock private TranslatorLocale translatorLocale;
  @Mock private UserBuilder userBuilder;
  @Mock private UserDetailsBuilder userDetailsBuilder;

  @InjectMocks UsersServiceImpl userService;

  private static final long USER_ID = 1L;
  private static final String LOGIN = "Valid Login";
  private static final String PASSWORD = "Valid Password";
  private static final BigDecimal BALANCE = new BigDecimal("100");
  private final Users user = new Users(USER_ID, LOGIN, PASSWORD, BALANCE, Role.USER);
  private final List<Users> users = new ArrayList<>();
  private final List<UsersDto> usersDtos = new ArrayList<>();
  private static final int DEFAULT_PAGE_PARAMETER = 1;
  private static final int DEFAULT_SIZE_PARAMETER = 1;
  private static final long FIRST_ORDER_ID = 1L;
  private static final BigDecimal FIRST_ORDER_COST = BigDecimal.valueOf(10);
  private static final LocalDateTime FIRST_ORDER_CREATE_TIME = LocalDateTime.now();
  private final Orders order =
      new Orders(FIRST_ORDER_ID, FIRST_ORDER_COST, FIRST_ORDER_CREATE_TIME);
  private final Set<TagDto> emptyTagSet = new HashSet<>();
  private final GiftCertificate giftCertificate =
      new GiftCertificate(
          1L,
          "anotherValidName1",
          "validDescription1",
          BigDecimal.valueOf(12),
          14,
          LocalDateTime.now(),
          LocalDateTime.now());
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
          USER_ID,
          expectedGiftCertificateDtos);
  private final UsersDto usersDto = new UsersDto(USER_ID, LOGIN, BALANCE, Set.of(orderDto));
  private final List<Long> giftCertificateIds = Collections.singletonList(1L);
  private final GiftCertificateDtoIds giftCertificateDtoIds = new GiftCertificateDtoIds();
  private static final long MOST_WIDE_TAG_ID = 2L;
  private static final String MOST_WIDE_TAG_NAME = "The Most";
  private final Tag mostWideTag = new Tag(MOST_WIDE_TAG_ID, MOST_WIDE_TAG_NAME);
  private final TagDto expectedMostWideTag = new TagDto(MOST_WIDE_TAG_ID, MOST_WIDE_TAG_NAME);
  private final UsersCreatingDto usersCreatingDto = new UsersCreatingDto(LOGIN, PASSWORD, PASSWORD);

  @BeforeEach
  void setUp() {
    users.add(user);
    usersDtos.add(usersDto);
    giftCertificateDtoIds.setGiftCertificateDtoIds(giftCertificateIds);
  }

  @Test
  void testGetAll_shouldReturnUsers_whenTheyAreExistInDatabase() {
    // given
    when(pageValidator.isValid(any())).thenReturn(true);
    when(userRepository.getEntityListWithPagination(any(), anyInt(), anyInt())).thenReturn(users);
    when(userDtoBuilder.build(user)).thenReturn(usersDto);
    // when
    List<UsersDto> actualUsersDtos =
        userService.getAll(DEFAULT_PAGE_PARAMETER, DEFAULT_SIZE_PARAMETER);
    // then
    assertEquals(usersDtos, actualUsersDtos);
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
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    // when
    // then
    assertThrows(
        EmptyOrderException.class,
        () -> userService.makeOrder(USER_ID, new GiftCertificateDtoIds()));
  }

  @Test
  void testMakeOrder_shouldThrowEmptyOrderException_whenOrderIsEmpty() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    // when
    // then
    assertThrows(
        EmptyOrderException.class,
        () -> userService.makeOrder(USER_ID, new GiftCertificateDtoIds()));
  }

  @Test
  void testMakeOrder_shouldThrowInsufficientFundInAccount_whenUserAccountLessThanOrderCost() {
    // given
    Users poorUser = user;
    poorUser.setBalance(BigDecimal.valueOf(0));
    when(userRepository.getEntity(any())).thenReturn(Optional.of(poorUser));
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.of(giftCertificate));
    // when
    // then
    assertThrows(
        InsufficientFundInAccount.class,
        () -> userService.makeOrder(USER_ID, giftCertificateDtoIds));
  }

  @Test
  void testMakeOrder_shouldThrowEntityNotFoundException_whenUserWasNotUpdated() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    // when
    when(translatorLocale.toLocale(any()))
        .thenReturn(String.format("Requested resource with id = %s not found.", user.getId()));
    // then
    assertThrows(
        EntityNotFoundException.class, () -> userService.makeOrder(USER_ID, giftCertificateDtoIds));
  }

  @Test
  void testMakeOrder_shouldThrowEntityNotFoundException_whenUserNotUpdated() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.of(giftCertificate));
    // when
    when(translatorLocale.toLocale(any()))
        .thenReturn(String.format("Requested resource with id = %s not found.", user.getId()));
    // then
    assertThrows(
        EntityNotFoundException.class, () -> userService.makeOrder(USER_ID, giftCertificateDtoIds));
  }

  @Test
  void testMakeOrder_shouldThrowServiceException_whenOrdersWasNotSaved() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(userDtoBuilder.build(any())).thenReturn(usersDto);
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.of(giftCertificate));
    when(userRepository.update(user)).thenReturn(Optional.of(user));
    // when
    // then
    assertThrows(
        ServiceException.class, () -> userService.makeOrder(USER_ID, giftCertificateDtoIds));
  }

  @Test
  void testMakeOrder_shouldOrder_whenUserMadeOrderSuccessful() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(userDtoBuilder.build(any())).thenReturn(usersDto);
    when(giftCertificateRepository.getEntity(any())).thenReturn(Optional.of(giftCertificate));
    when(userRepository.update(user)).thenReturn(Optional.of(user));
    when(ordersRepository.save(any())).thenReturn(Optional.of(order));
    when(ordersDtoBuilder.build(any())).thenReturn(orderDto);
    // when
    Optional<OrdersDto> actualOptionalOrdersDto =
        userService.makeOrder(USER_ID, giftCertificateDtoIds);
    // then
    assertEquals(Optional.of(orderDto), actualOptionalOrdersDto);
  }

  @Test
  void testGetById_shouldReturnUser_whenUserIsExistsInDataBase() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(userDtoBuilder.build(any())).thenReturn(usersDto);
    // when
    UsersDto actualUsersDto = userService.getById(USER_ID).orElse(new UsersDto());
    // then
    assertEquals(usersDto, actualUsersDto);
  }

  @Test
  void testGetMostWidelyUsedTag_shouldReturnTag_whenItIsExists() {
    // given
    when(tagRepository.getEntity(any())).thenReturn(Optional.of(mostWideTag));
    when(tagDtoBuilder.build(mostWideTag)).thenReturn(expectedMostWideTag);
    // when
    Optional<TagDto> actualOptionalTag = userService.getMostWidelyUsedTagByUserId(USER_ID);
    // then
    assertEquals(Optional.of(expectedMostWideTag), actualOptionalTag);
  }

  @Test
  void testSave_shouldReturnOptionalUserDto_whenSavedWasSuccess() {
    // given
    when(userValidator.isValid(any())).thenReturn(true);
    when(userRepository.getEntity(any())).thenReturn(Optional.empty());
    when(userRepository.save(any())).thenReturn(Optional.of(user));
    when(userBuilder.buildForSave(usersCreatingDto)).thenReturn(user);
    when(userDtoBuilder.build(user)).thenReturn(usersDto);
    // when
    Optional<UsersDto> actualOptionalUser = userService.save(usersCreatingDto);
    // then
    assertEquals(Optional.of(usersDto), actualOptionalUser);
  }

  @Test
  void testSave_shouldUserLoginExistsException_whenUserIsExistsInDatabase() {
    // given
    when(userValidator.isValid(any())).thenReturn(true);
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(translatorLocale.toLocale(any()))
        .thenReturn(
            String.format(
                "The User with this name (%s) is already in the database.", user.getId()));
    // when
    // then
    assertThrows(UserLoginExistsException.class, () -> userService.save(usersCreatingDto));
  }

  @Test
  void testLoadUserByUsername_shouldThrowUsernameNotFoundException_whenUserNotExists() {
    // given
    when(userRepository.getEntity(any())).thenReturn(Optional.empty());
    when(translatorLocale.toLocale(any()))
        .thenReturn(
            String.format("User with login = %s does not exists in database.", user.getId()));
    // when
    // then
    assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(LOGIN));
  }

  @Test
  void testLoadUserByUsername_shouldReturnUserDetails_whenUserIsExists() {
    // given
    UserDetails userDetails = create(user);
    when(userRepository.getEntity(any())).thenReturn(Optional.of(user));
    when(userDetailsBuilder.build(user)).thenReturn(userDetails);
    // when
    UserDetails actualUserDetails = userService.loadUserByUsername(LOGIN);
    // then
    assertEquals(userDetails, actualUserDetails);
  }

  private UserDetails create(Users user) {
    return new User(
        user.getLogin(),
        user.getPassword(),
        true,
        true,
        true,
        true,
        Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
  }
}
