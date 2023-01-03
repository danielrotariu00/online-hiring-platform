import { Job } from "./job";
import { JobApplicationStatus } from "./job_application_status";

export class JobApplication {
  id?: number;
  userId?: number;
  jobId?: number;
  statusId?: number;
  updatedAt: string;
  job?: Job;
  status?: JobApplicationStatus;
}
