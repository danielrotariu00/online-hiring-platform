package com.licenta.database.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="job")
public class Job {

    @Id
    @NotEmpty
    private String id;

    @NotEmpty
    private String title;

    @ManyToOne
    @JoinColumn(name = "company_industry_id", nullable=false)
    private CompanyIndustry companyIndustry;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    // TODO: add another table for this
    private String workType; // on-site, remote, hybrid

    // TODO: add another table for this
    private String jobType; // full-time, part-time, seasonal, temporary

    // TODO: add another table for this
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director

    @Column(columnDefinition = "TEXT")
    private String description;

    private String postedAt;
}
