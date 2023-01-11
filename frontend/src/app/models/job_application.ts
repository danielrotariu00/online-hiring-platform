import { Job } from "./job";
import { JobApplicationStatus } from "./job_application_status";
import { UserDetails } from "./user_details";

export class JobApplication {
  id?: number;
  userId?: number;
  jobId?: number;
  statusId?: number;
  updatedAt: Date;
  job?: Job;
  status?: JobApplicationStatus;
  userDetails?: UserDetails;
  formattedUpdatedAt: string;
}
