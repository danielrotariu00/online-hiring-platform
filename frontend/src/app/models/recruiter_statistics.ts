import { Review } from "./review";

export interface RecruiterStatistics {
  recruiterId: number;
  averageRating: number;
  reviews: Review[];
  totalJobApplications: number;
  currentSubmittedJobApplications: number;
  currentReviewedJobApplications: number;
  currentInProgressJobApplications: number;
  currentOfferExtendedJobApplications: number;
  currentHiredJobApplications: number;
  currentOnHoldJobApplications: number;
  currentDeclinedJobApplications: number;
  currentWithdrawnJobApplications: number;
}
