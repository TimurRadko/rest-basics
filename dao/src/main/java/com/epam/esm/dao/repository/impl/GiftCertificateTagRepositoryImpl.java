package com.epam.esm.dao.repository.impl;

import com.epam.esm.dao.entity.GiftCertificateTag;
import com.epam.esm.dao.repository.GiftCertificateTagRepository;
import com.epam.esm.dao.specification.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateTagRepositoryImpl implements GiftCertificateTagRepository {
  private EntityManager entityManager;

  @Autowired
  public GiftCertificateTagRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<GiftCertificateTag> getEntityListBySpecification(
      Specification<GiftCertificateTag> specification) {
    return null;
  }

  @Override
  public Optional<GiftCertificateTag> getEntityBySpecification(
      Specification<GiftCertificateTag> specification) {
    return Optional.empty();
  }
}
