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


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="educational_institution")
public class EducationalInstitution {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique=true)
    private String name;

    @NotEmpty
    private String photo;

    @NotEmpty
    private String website;
}
