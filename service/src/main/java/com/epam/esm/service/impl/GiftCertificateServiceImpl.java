package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.gift.GetGiftCertificatesByTagNameSpecification;
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
    private GiftCertificateValidator giftCertificateValidator;
    private GiftCertificateExtractor extractor;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      GiftCertificateValidator giftCertificateValidator,
                                      GiftCertificateExtractor extractor) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
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
            tags = saveAllTags(tags);

            giftCertificateDto = new GiftCertificateDto(giftCertificate, tags);
            return Optional.of(giftCertificateDto);
        } else {
            throw new ServiceException(giftCertificateValidator.getErrorMessage());
        }
    }

    private Set<Tag> saveAllTags(Set<Tag> tags) {
        Set<Tag> createdTags = new HashSet<>();
        for (Tag tag : tags) {
            Optional<Tag> optionalTags = tagRepository.save(tag);
            if (optionalTags.isPresent()) {
                tag = optionalTags.get();
            }
            createdTags.add(tag);
        }
        return createdTags;
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

    @Override
    public Optional<GiftCertificateDto> update(GiftCertificateDto giftCertificateDto) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
