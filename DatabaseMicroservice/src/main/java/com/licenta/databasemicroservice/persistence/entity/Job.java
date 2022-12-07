package com.licenta.databasemicroservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="job")
public class Job {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String title;

    @ManyToOne
    @JoinColumn(name = "recruiter_id", nullable=false)
    private User recruiter;

    @ManyToOne
    @JoinColumn(name = "company_industry_id", nullable=false)
    private CompanyIndustry companyIndustry;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name="work_type_id")
    private WorkType workType; // on-site, remote, hybrid

    @ManyToOne
    @JoinColumn(name="job_type_id")
    private JobType jobType; // full-time, part-time, seasonal, temporary

    @ManyToOne
    @JoinColumn(name="experience_level_id")
    private ExperienceLevel experienceLevel; // internship, entry-level, associate, mid-senior level, director

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name="job_status_id")
    private JobStatus jobStatus; // open, closed

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "posted_at")
    private Date postedAt;
}
