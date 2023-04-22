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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="user_educational_experience")
public class UserEducationalExperience {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_id", nullable=false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "educational_institution_id", nullable=false)
    private EducationalInstitution educationalInstitution;

    @NotEmpty
    private String speciality;

    @NotEmpty
    private String title;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private String description;
}
