package com.test.tracker.core.repository;

import com.test.tracker.core.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByAndOrderByCreatedDt();

    @Query(
            nativeQuery = true,
            value = "select * from task as t " +
                    "where t.author_id = (select * from user_subdivision as us " +
                    "                    where as.subdivision_id = :subdivision_id)" +
                    "order by t.created_dt")
    List<TaskEntity> findAllBySubdivision(@Param("subdivision_id") Long subdivisionId);
}
