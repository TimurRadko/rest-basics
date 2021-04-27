package com.epam.esm.dao.audit;

import com.epam.esm.dao.entity.AuditHistory;
import com.epam.esm.dao.entity.TableEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import static com.epam.esm.dao.audit.Action.DELETED;
import static com.epam.esm.dao.audit.Action.INSERTED;
import static com.epam.esm.dao.audit.Action.UPDATED;

public class AuditListener {

  @PrePersist
  public void prePersist(TableEntity tableEntity) {
    perform(tableEntity, INSERTED);
  }

  @PreUpdate
  public void preUpdate(TableEntity tableEntity) {
    perform(tableEntity, UPDATED);
  }

  @PreRemove
  public void preRemove(TableEntity tableEntity) {
    perform(tableEntity, DELETED);
  }

  private void perform(TableEntity tableEntity, Action action) {
    EntityManager entityManager = BeanUtil.getBean(EntityManager.class);
    entityManager.persist(new AuditHistory(tableEntity, action));
  }
}
