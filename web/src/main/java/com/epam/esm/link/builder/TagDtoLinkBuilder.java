package com.epam.esm.link.builder;

import com.epam.esm.service.dto.TagDto;
import com.epam.esm.controller.TagsController;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagDtoLinkBuilder implements LinkBuilder<TagDto> {

  @Override
  public TagDto build(TagDto tagDto) {
    return tagDto.add(linkTo(methodOn(TagsController.class).get(tagDto.getId())).withSelfRel());
  }
}
