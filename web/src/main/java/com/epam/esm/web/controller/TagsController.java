package com.epam.esm.web.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagsController {
  private TagService tagService;

  @Autowired
  public TagsController(TagService tagService) {
    this.tagService = tagService;
  }

  @GetMapping()
  public List<TagDto> getAll(@RequestParam(value = "sort", required = false) String sort) {
    return tagService.getAll(sort);
  }

  @GetMapping("/{id}")
  public TagDto get(@PathVariable Long id) throws ServiceException {
    Optional<TagDto> optionalTagDto = tagService.getById(id);
    return optionalTagDto.orElseThrow(
        () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TagDto save(@RequestBody TagDto tagDto) {
    Optional<TagDto> optionalTag = tagService.save(tagDto);
    return optionalTag.orElseThrow(
        () -> new TagAlreadyExistsException("The Tag already exists in the DB"));
  }

  @DeleteMapping(value = "/{id}")
  public String delete(@PathVariable long id) {
    tagService
        .getById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Requested resource not found (id = " + id + ")"));
    tagService.delete(id);
    return "The Tag with id = " + id + " was deleted";
  }
}
