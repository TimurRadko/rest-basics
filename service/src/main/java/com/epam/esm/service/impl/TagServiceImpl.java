package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.tag.GetAllTagsSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByIdSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByNameSpecification;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagValidator tagValidator;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, TagValidator tagValidator) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
    }

    @Override
    public List<Tag> getAll(String sort) {
        return tagRepository.getEntitiesListBySpecification(new GetAllTagsSpecification(sort));
    }

    @Override
    public Optional<Tag> getById(long id) {
        return tagRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
    }

    @Override
    public Optional<Tag> save(Tag tag) {
        if (tagValidator.validate(tag)) {
            String name = tag.getName();
            Optional<Tag> optionalExistingTag = tagRepository.getEntityBySpecification(new GetTagByNameSpecification(name));
            return tagRepository.save(optionalExistingTag.orElseThrow(() -> new ServiceException("Empty Tag")));
        } else {
            throw new ServiceException(tagValidator.getErrorMessage());
        }
    }

    @Override
    public int delete(long id) {
        return tagRepository.delete(id);
    }
}
