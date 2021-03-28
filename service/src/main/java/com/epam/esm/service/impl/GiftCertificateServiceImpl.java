package com.epam.esm.service.impl;

import com.epam.esm.persistence.repository.GiftCertificateRepository;
import com.epam.esm.persistence.entity.GiftCertificate;
import com.epam.esm.persistence.specification.gift.GetAllGiftsSpecification;
import com.epam.esm.persistence.specification.gift.GetGiftByIdSpecification;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificateRepository.getListBySpecification(new GetAllGiftsSpecification());
    }

    @Override
    public GiftCertificate getById(long id) {
        return giftCertificateRepository.getEntityBySpecification(new GetGiftByIdSpecification(id));
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {

        //TODO: implement the variable validator

        return giftCertificateRepository.create(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
