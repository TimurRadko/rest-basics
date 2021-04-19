package com.epam.esm.dao.repository;

import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.specification.Specification;

import java.util.List;
import java.util.Optional;

/** * This interface describes a common operations with Tag's Entities situated in the DB */
public interface TagRepository {

  Optional<Tag> save(Tag tag);

  List<Tag> getTagsBySpecification(Specification specification);

  List<Tag> getTagsByGiftCertificateId(long id);

  Optional<Tag> getTagById(long id);

  Optional<Tag> getTagByName(String name);

  int delete(long id);
}
