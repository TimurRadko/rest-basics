package com.epam.esm.persistence.dao;

import com.epam.esm.persistence.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao {
    List<GiftCertificate> getAll();
}
