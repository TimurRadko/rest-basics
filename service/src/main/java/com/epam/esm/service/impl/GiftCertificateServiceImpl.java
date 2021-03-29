package com.epam.esm.service.impl;

import com.epam.esm.persistence.entity.Tag;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.persistence.specification.tag.GetTagsListByGiftCertificatesIdSpecification;
import com.epam.esm.service.dto.GiftCertificateDto;
import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.specification.gift.GetAllGiftCertificatesSpecification;
import com.epam.esm.persistence.specification.tag.GetTagByIdSpecification;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepository giftCertificateRepository;
    private TagRepository tagRepository;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public Optional<GiftCertificateDto> save(GiftCertificateDto giftCertificateDto) {

//        //TODO: implement the variable validator
//        return giftCertificateRepository.create(giftCertificateDto);
        return Optional.empty();
    }

    @Override
    public List<GiftCertificateDto> getAll() {
        List<GiftCertificate> giftCertificates =
                giftCertificateRepository.getListBySpecification(new GetAllGiftCertificatesSpecification());
        return giftCertificates.stream().map((this::createGiftCertificateDto)).collect(Collectors.toList());
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
