package com.epam.esm.service;

import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.service.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/** * This interface describes specific realization of CRUD operation on User Entities */
public interface UserService extends Service<UserDto> {
  /**
   * * This method describes a general getAll (getting a list of all UserDto) operation for all
   * UserDtos, from persistence layer
   *
   * @return List<UserDto> - List of UserDto contained in one of all tables in the DB
   */
  List<UserDto> getAll();

  /**
   * * This method describes a execute order by User
   *
   * @param id - passed into the method id User's parameter that required for work with the DB
   * @param giftCertificateDtos - passed into the method Set<GiftCertificateDto> for execute order
   * @param sort - the parameter, which describes rules of sorting getting data
   * @return Optional<UserDto> - container that is contained UserDto (a typed parameter)
   */
  Optional<UserDto> makeOrder(Long id, Set<GiftCertificateDto> giftCertificateDtos, String sort);
}
