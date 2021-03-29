package com.epam.esm.service;

import com.epam.esm.persistence.entity.Tag;

import java.util.List;

public interface TagService extends Service<Tag> {
    List<Tag> getTagsByGiftCertificateId(long id);
}
