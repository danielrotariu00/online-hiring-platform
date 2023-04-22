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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company_industry")
public class CompanyIndustry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "industry_id", nullable=false)
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable=false)
    private Company company;

    // child entities
    @OneToMany(mappedBy = "companyIndustry", cascade = CascadeType.REMOVE)
    private Set<CompanyIndustryFollower> companyIndustryFollowers = new LinkedHashSet<>();
}
