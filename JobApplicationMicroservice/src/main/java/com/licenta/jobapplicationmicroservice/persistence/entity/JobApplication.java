package com.licenta.jobapplicationmicroservice.persistence.entity;

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
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="job_application")
public class JobApplication {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private Long userId;

    @Column(nullable=false)
    private Long jobId;

    @ManyToOne
    @JoinColumn(name="job_application_status_id")
    private JobApplicationStatus status;

    @Column(nullable=false)
    private LocalDateTime updatedAt;
}
