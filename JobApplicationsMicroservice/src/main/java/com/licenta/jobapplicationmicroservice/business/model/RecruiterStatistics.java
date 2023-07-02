package com.licenta.jobapplicationmicroservice.business.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruiterStatistics {

    private Long recruiterId;
    private Double averageRating;
    private List<Review> reviews;
    private Integer totalJobApplications;
    private Integer currentSubmittedJobApplications;
    private Integer currentReviewedJobApplications;
    private Integer currentInProgressJobApplications;
    private Integer currentOfferExtendedJobApplications;
    private Integer currentHiredJobApplications;
    private Integer currentOnHoldJobApplications;
    private Integer currentDeclinedJobApplications;
    private Integer currentWithdrawnJobApplications;
}
