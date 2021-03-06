package com.test.tracker.core.repository;

import com.test.tracker.core.model.entity.SubDivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDivisionRepository extends JpaRepository<SubDivisionEntity, Long> {
}
