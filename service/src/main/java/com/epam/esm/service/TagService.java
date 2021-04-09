package com.epam.esm.service;

import com.epam.esm.service.dto.TagDto;

import java.util.List;

/** * This interface describes specific realization of CRUD operation on Tag Entities */
public interface TagService extends Service<TagDto> {
  /**
   * * This method describes a general getAll (getting a list of all TagDto) operation for all
   * TagDtos, from persistence layer
   *
   * @param sort - the parameter, which describes rules of sorting getting data
   * @return List<TagDto> - List of TagDto contained in one of all tables in the DB
   */
  List<TagDto> getAll(String sort);
}
