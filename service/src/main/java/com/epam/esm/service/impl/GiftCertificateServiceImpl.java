package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.GiftCertificateTag;
import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.GiftCertificateTagRepository;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByTagNameSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByNameSpecification;
import com.epam.esm.persistence.specification.tag.GetTagsListByGiftCertificatesIdSpecification;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByIdSpecification;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.extractor.GiftCertificateExtractor;
import com.epam.esm.service.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;
    private GiftCertificateTagRepository giftCertificateTagRepository;
    private GiftCertificateValidator giftCertificateValidator;
    private GiftCertificateExtractor extractor;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateTagRepository giftCertificateTagRepository,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateExtractor extractor) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateTagRepository = giftCertificateTagRepository;
        this.giftCertificateValidator = giftCertificateValidator;
        this.extractor = extractor;
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateValidator.validate(giftCertificateDto)) {
            GiftCertificate giftCertificate = extractor.extract(giftCertificateDto);
            giftCertificate = giftCertificateRepository.save(giftCertificate)
                    .orElseThrow(() -> new ServiceException("The Gift Certificate didn't save"));

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
        if (tags == null) {
            return createdTags;
        }

        for (Tag tag : tags) {
            String name = tag.getName();
            Optional<Tag> optionalExistingTag
                    = tagRepository.getEntityBySpecification(new GetTagByNameSpecification(name));
            Long existingTagId = null;
            if (optionalExistingTag.isPresent()) {
                Tag existingTag = optionalExistingTag.get();
                createdTags.add(existingTag);
                existingTagId = existingTag.getId();
            } else {
                Optional<Tag> createdTag = tagRepository.saveIfNotExist(tag);
                createdTag.ifPresent(createdTags::add);
                if (createdTag.isPresent()) {
                    existingTagId = createdTag.get().getId();
                }
            }
            saveGiftCertificateTag(giftCertificateId, existingTagId);
        }
        return createdTags;
    }

    private void saveGiftCertificateTag(long giftCertificateId, Long existingTagId) {
        GiftCertificateTag giftCertificateTag = new GiftCertificateTag();
        giftCertificateTag.setTagId(existingTagId);
        giftCertificateTag.setGiftCertificateId(giftCertificateId);
        giftCertificateTagRepository.save(giftCertificateTag);
    }

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificates =
                giftCertificateRepository.getListBySpecification(
                        new GetAllGiftCertificatesSpecification());
        return giftCertificates.stream()
                .map((this::createGiftCertificateDto))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificatesByTagName(String name) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.getListBySpecification(
                new GetGiftCertificatesByTagNameSpecification(name));
        return giftCertificates.stream()
                .map((this::createGiftCertificateDto))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GiftCertificateDto> getById(long id) {
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
        if (optionalGiftCertificate.isPresent()) {
            GiftCertificate giftCertificate = optionalGiftCertificate.get();
            return Optional.of(createGiftCertificateDto(giftCertificate));
        }
        return Optional.empty();
    }

    private GiftCertificateDto createGiftCertificateDto(GiftCertificate giftCertificate) {
        long id = giftCertificate.getId();
        List<Tag> tags = tagRepository.getListBySpecification(new GetTagsListByGiftCertificatesIdSpecification(id));
        return new GiftCertificateDto(giftCertificate, Set.copyOf(tags));
    }


    //TODO: This method is not finished yet!!!!
    @Override
    public Optional<GiftCertificateDto> update(long id, GiftCertificateDto giftCertificateDto) {
        Optional<GiftCertificate> optionalGiftCertificate =
                giftCertificateRepository.getEntityBySpecification(new GetTagByIdSpecification(id));
        if (optionalGiftCertificate.isPresent()) {
            GiftCertificate giftCertificate = extractor.extract(giftCertificateDto);
            giftCertificateRepository.update(giftCertificate);
        }
        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        giftCertificateRepository.delete(id);
    }
}
