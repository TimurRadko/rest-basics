package com.epam.esm.service;

import com.epam.esm.persistence.dao.GiftCertificateDao;
import com.epam.esm.persistence.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    public List<GiftCertificate> getAll() {
        return giftCertificateDao.getAll();
    }
}
