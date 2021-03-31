package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Tag;

import java.util.Optional;

public interface TagRepository extends Repository<Tag> {
    Optional<Tag> saveIfNotExist(Tag tag);

    void delete(long id);
}
