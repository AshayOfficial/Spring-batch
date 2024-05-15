package org.demo.batch.repository;

import org.demo.batch.entity.BusinessEmploymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringBatchRepository extends JpaRepository<BusinessEmploymentEntity, Integer> {
}
