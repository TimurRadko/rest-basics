package com.epam.esm.web.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.exception.EntityNotFoundException;
import com.epam.esm.web.exception.TagAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tags")
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
  public TagDto save(
      @RequestBody TagDto tagDto, HttpServletRequest request, HttpServletResponse response) {
    Optional<TagDto> optionalTag = tagService.save(tagDto);
    TagDto savedTagDto =
        optionalTag.orElseThrow(
            () ->
                new TagAlreadyExistsException(
                    "The tag with this name ("
                        + tagDto.getName()
                        + ") is already in the database"));
    Long id = savedTagDto.getId();
    String url = request.getRequestURL().toString();
    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);
    return savedTagDto;
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    tagService.delete(id);
  }
}
