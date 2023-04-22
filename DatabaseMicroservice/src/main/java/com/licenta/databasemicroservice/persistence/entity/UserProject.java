package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_project")
public class UserProject {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable=false)
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;
}
