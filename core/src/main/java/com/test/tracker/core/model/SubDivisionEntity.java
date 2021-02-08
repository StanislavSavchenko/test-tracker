package com.test.tracker.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.tracker.core.util.LocalDateTimeDeserializer;
import com.test.tracker.core.util.LocalDateTimeSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_data")
@Data
public class SubDivisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subdivision_id_seq")
    @SequenceGenerator(name = "subdivision_id_seq", sequenceName = "subdivision_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany(mappedBy = "subDivisionSet")
    Set<UserEntity> userSet;

    @Column(name = "created_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedDt;
}
