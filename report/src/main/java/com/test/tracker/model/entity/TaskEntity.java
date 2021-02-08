package com.test.tracker.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.tracker.std.LocalDateTimeDeserializer;
import com.test.tracker.std.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @ApiModelProperty(readOnly = true, position = -1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "performer_id", nullable = false)
    private Long performerId;

    @Column(name = "created_dt", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT now()")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonIgnore
    private LocalDateTime createDate;

    @Column(name = "updated_dt", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT now()")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateDate;

}