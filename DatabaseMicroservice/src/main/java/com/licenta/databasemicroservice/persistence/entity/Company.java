package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.LinkedHashSet;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;
    private String photo;
    private String description;
    private String website;
    private Integer cityId;

    // child entities

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<CompanyIndustry> companyIndustries = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<CompanyManager> companyManagers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    private Set<CompanyRecruiter> companyRecruiters = new LinkedHashSet<>();
}
