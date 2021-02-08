package com.test.tracker.core.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.test.tracker.core.std.LocalDateTimeDeserializer;
import com.test.tracker.core.std.LocalDateTimeSerializer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Table(name = "user_data")
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_data_id_seq")
    @SequenceGenerator(name = "user_data_id_seq", sequenceName = "user_data_id_seq", allocationSize = 1, initialValue = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_subdivision",
            joinColumns = @JoinColumn(name = "subdivision_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<SubDivisionEntity> subDivisionSet;

    @Column(name = "created_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDt;

    @Column(name = "updated_dt")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedDt;
}
