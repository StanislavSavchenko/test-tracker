package com.test.tracker.core.repository;

import com.test.tracker.core.model.entity.TaskAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachmentEntity, Long> {

    Optional<TaskAttachmentEntity> findOneByIdAndTaskId(Long attachmentId, Long taskId);

    List<TaskAttachmentEntity> findAllByTaskId(Long taskId);

}
