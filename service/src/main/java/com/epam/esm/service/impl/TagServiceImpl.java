package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.tag.GetAllTagsSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByIdSpecification;
import com.epam.esm.persistence.specification.tag.GetTagsListByGiftCertificatesIdSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;
    private TagValidator tagValidator;

    public TagServiceImpl(TagRepository tagRepository, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getListBySpecification(new GetAllTagsSpecification());
    }

    @Override
    public Optional<Tag> getById(long id) {
        return tagRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
    }

    @Override
    public List<Tag> getTagsByGiftCertificateId(long id) {
        return tagRepository.getListBySpecification(new GetTagsListByGiftCertificatesIdSpecification(id));
    }

    @Override
    public Optional<Tag> save(Tag tag) {
        if (tagValidator.validate(tag)) {
            return tagRepository.save(tag);
        } else {
            throw new ServiceException(tagValidator.getErrorMessage());
        }
    }

    @Override
    public Optional<Tag> update(Tag tag) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
