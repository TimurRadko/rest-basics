package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.GiftCertificateTag;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateTagRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.gift.GetGiftCertificateByIdSpecification;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByPartNameOrDescriptionSpecification;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByTagNameSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByNameSpecification;
import com.epam.esm.persistence.specification.tag.GetAllTagsByGiftCertificatesIdSpecification;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.builder.GiftCertificateBuilder;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateTagRepository giftCertificateTagRepository;
    private final GiftCertificateValidator giftCertificateValidator;
    private final GiftCertificateBuilder builder;
    private static final String CERTIFICATE_NOT_EXISTS_IN_THE_DB = "The Gift Certificate not exists in the DB";

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateTagRepository giftCertificateTagRepository,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateBuilder builder) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateTagRepository = giftCertificateTagRepository;
        this.giftCertificateValidator = giftCertificateValidator;
        this.builder = builder;
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateValidator.validate(giftCertificateDto)) {
            GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
            giftCertificate = giftCertificateRepository.save(giftCertificate)
                    .orElseThrow(() -> new ServiceException("The Gift Certificate wasn't saved"));

            Set<Tag> tags = giftCertificateDto.getTags();
            tags = saveNotRepeatableTags(tags, giftCertificate.getId());

            giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
            return Optional.of(giftCertificateDto);
        } else {
            throw new ServiceException(giftCertificateValidator.getErrorMessage());
        }
    }

    private Set<Tag> saveNotRepeatableTags(Set<Tag> tags, long giftCertificateId) {
        Set<Tag> createdTags = new HashSet<>();
        if (tags != null) {
            tags.forEach((tag -> createdTags.add(saveTagIfNotExist(tag, giftCertificateId))));
        }
        return createdTags;
    }

    private Tag saveTagIfNotExist(Tag tag, long giftCertificateId) {
        Optional<Tag> optionalExistingTag
                = tagRepository.getEntityBySpecification(new GetTagByNameSpecification(tag.getName()));
        if (optionalExistingTag.isEmpty()) {
            Tag createdTag = tagRepository.save(tag)
                    .orElseThrow(() -> new ServiceException("The Tag wasn't saved"));
            GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
            giftCertificateTag.setTagId(createdTag.getId());
            giftCertificateTag.setGiftCertificateId(giftCertificateId);
            giftCertificateTagRepository.save(giftCertificateTag);
            return createdTag;
        }
        return tag;
    }

    @Override
    public List<GiftCertificateDto> getAll(String sort) {
        List<GiftCertificate> giftCertificates =
                giftCertificateRepository.getEntitiesListBySpecification(
                        new GetAllGiftCertificatesSpecification(sort));
        return giftCertificates.stream()
                .map((this::createGiftCertificateDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificatesByNameOrDescriptionPart(
            String name,
            String description,
            String sort) {

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (name != null || description != null) {
            giftCertificates = giftCertificateRepository.getEntitiesListBySpecification(
                    new GetGiftCertificatesByPartNameOrDescriptionSpecification(name, description, sort));
        }
        return giftCertificates.stream()
                .map((this::createGiftCertificateDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificatesByTagName(
            List<GiftCertificateDto> giftCertificateDtos,
            String TagName,
            String sort) {
        if (giftCertificateDtos.size() == 0) {
            List<GiftCertificate> giftCertificates = giftCertificateRepository.getEntitiesListBySpecification(
                    new GetGiftCertificatesByTagNameSpecification(TagName, sort));
            return giftCertificates.stream()
                    .map((this::createGiftCertificateDto))
                    .collect(Collectors.toList());
        }

        return giftCertificateDtos
                .stream().filter((giftCertificateDto -> giftCertificateDto
                        .getTags()
                        .stream()
                        .anyMatch(tag -> tag.getName().equalsIgnoreCase(TagName))))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificateDto> getById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateRepository.getEntityBySpecification(new GetGiftCertificateByIdSpecification(id));
        if (optionalGiftCertificate.isPresent()) {
            GiftCertificate giftCertificate = optionalGiftCertificate.get();
            return Optional.of(createGiftCertificateDto(giftCertificate));
        }
        return Optional.empty();
    }

    private GiftCertificateDto createGiftCertificateDto(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        List<Tag> tags = tagRepository.getEntitiesListBySpecification(
                new GetAllTagsByGiftCertificatesIdSpecification(id));
        return new GiftCertificateDto(giftCertificate, Set.copyOf(tags));
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto) {
        if (giftCertificateValidator.validate(giftCertificateDto)) {
            GiftCertificate giftCertificate = builder.buildFromDto(giftCertificateDto);
            GiftCertificate existingGiftCertificate =
                    giftCertificateRepository.getEntityBySpecification(new GetGiftCertificateByIdSpecification(id))
                            .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

            giftCertificate = builder.buildNewParameterGiftCertificate(existingGiftCertificate, giftCertificate);
            GiftCertificate newGiftCertification = giftCertificateRepository.update(giftCertificate)
                    .orElseThrow(() -> new ServiceException(CERTIFICATE_NOT_EXISTS_IN_THE_DB));

            Set<Tag> tags = giftCertificateDto.getTags();
            tags = saveNotRepeatableTags(tags, newGiftCertification.getId());

            List<Tag> existingTag =
                    tagRepository.getEntitiesListBySpecification(
                            new GetAllTagsByGiftCertificatesIdSpecification(id));
            tags.addAll(existingTag);

            giftCertificateDto = new GiftCertificateDto(newGiftCertification, tags);
            return Optional.of(giftCertificateDto);
        } else {
            throw new ServiceException(giftCertificateValidator.getErrorMessage());
        }
    }

    @Override
    public int delete(long id) {
        return giftCertificateRepository.delete(id);
    }
}
