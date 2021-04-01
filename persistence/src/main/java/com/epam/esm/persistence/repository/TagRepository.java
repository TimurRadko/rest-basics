package com.epam.esm.persistence.repository;

import com.epam.esm.persistence.entity.Tag;

public interface TagRepository extends Repository<Tag> {
    void delete(long id);
}
