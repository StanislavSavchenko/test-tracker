package com.test.tracker.core.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.tracker.core.model.views.Views;
import com.test.tracker.core.std.LocalDateTimeDeserializer;
import com.test.tracker.core.std.LocalDateTimeSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "task")
@Entity
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq")
    @SequenceGenerator(name = "task_id_seq", sequenceName = "task_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    @JsonView({Views.Retrieve.class})
    private Long id;

    @JsonView({Views.Retrieve.class, Views.Update.class})
    @Column(name = "name")
    private String name;

    @JsonView({Views.Retrieve.class, Views.Update.class})
    @Column(name = "topic")
    private String topic;

    @JsonView({Views.Retrieve.class, Views.Update.class})
    @Column(name = "status")
    private String status;

    @JsonView({Views.Retrieve.class, Views.Update.class})
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private UserEntity performer;

    @Column(name = "created_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedDt;
}
