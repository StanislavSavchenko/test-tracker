package com.test.tracker.core.repository;

import com.test.tracker.core.model.entity.TaskAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachmentEntity, Long> {
}
