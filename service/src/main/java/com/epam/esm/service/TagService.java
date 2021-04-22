package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;
import java.util.Optional;

/** * This interface describes specific realization of CRUD operation on Tag Entities */
public interface TagService extends Service<TagDto> {
  /**
   * * This method describes a general save (create) operation for all Tags, located in the DB
   *
   * @param tagDto - TagDto, which transmitted in the method as a args
   * @return Optional<TagDto> - container that is contained TagDto
   */
  Optional<TagDto> save(TagDto tagDto);
  /**
   * * This method describes a general getAll (getting a list of all TagDto) operation for all
   * TagDtos, from persistence layer
   *
   * @param sort - the parameter, which describes rules of sorting getting data
   * @return List<TagDto> - List of TagDto contained in one of all tables in the DB
   */
  List<TagDto> getAll(String sort);
  /**
   * * This method describes a general delete (deleting a Tag by parameter) operation for all Tags,
   * from persistence layer
   *
   * @param id - passed into the method id Tag's parameter that required for work with the DB
   * @return int - return value more than 0, when Entity was deleted
   */
  int delete(long id);
}
