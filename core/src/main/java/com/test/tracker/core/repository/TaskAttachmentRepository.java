package com.test.tracker.core.repository;

import com.test.tracker.core.model.TaskAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachmentEntity, Long> {
}
