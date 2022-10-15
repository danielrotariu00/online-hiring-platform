package com.licenta.database.persistence.models;

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
    @JoinColumn(name = "company_id", nullable=false)
    private Company company;

    @ManyToOne
    @JoinColumn(name="city_name")
    private City city;

    @ManyToOne
    @JoinColumn(name="country_name", nullable=false)
    private Country country;

    // TODO: add another table for this
    private String workType; // on-site, remote, hybrid

    // TODO: add another table for this
    private String jobType; // full-time, part-time, seasonal, temporary

    // TODO: add another table for this
    private String experienceLevel; // internship, entry-level, associate, mid-senior level, director

    // TODO: add another table for this
    private String industry; // IT, etc

    @Column(columnDefinition = "TEXT")
    private String description;

    private String createdAt;
}
