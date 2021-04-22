package com.epam.esm.service.builder.tag;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagBuilder {

  public Tag build(TagDto tagDto) {
    return new Tag(tagDto.getId(), tagDto.getName());
  }
}
