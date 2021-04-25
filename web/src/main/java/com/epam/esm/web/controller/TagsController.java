package com.epam.esm.web.controller;

import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.exception.EntityNotFoundException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.web.link.builder.LinkBuilder;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/tags")
public class TagsController {
  private final TagService tagService;
  private final LinkBuilder<TagDto> tagLinkBuilder;

  @Autowired
  public TagsController(TagService tagService, LinkBuilder<TagDto> tagLinkBuilder) {
    this.tagService = tagService;
    this.tagLinkBuilder = tagLinkBuilder;
  }

  @GetMapping()
  public List<TagDto> getAll(@RequestParam(value = "sort", required = false) String sort) {
    return tagService.getAll(sort).stream().map(tagLinkBuilder::build).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public TagDto get(@PathVariable Long id) throws ServiceException {
    return tagLinkBuilder.build(
        tagService
            .getById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Requested resource not found (id = " + id + ")")));
  }

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public TagDto save(
      @RequestBody TagDto tagDto, HttpServletRequest request, HttpServletResponse response) {
    Optional<TagDto> optionalTag = tagService.save(tagDto);
    TagDto savedTagDto =
        optionalTag.orElseThrow(() -> new EntityNotFoundException("The Tag didn't add to DB"));

    Long id = savedTagDto.getId();
    String url = request.getRequestURL().toString();
    response.setHeader(HttpHeaders.LOCATION, url + "/" + id);

    return tagLinkBuilder.build(savedTagDto);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    tagService.delete(id);
  }
}
