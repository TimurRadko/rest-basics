package com.epam.esm.service.builder.tag;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagDtoBuilder {

  public TagDto build(Tag tag) {
    return new TagDto(tag.getId(), tag.getName());
  }
}
