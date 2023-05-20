import { Job } from "./job";
import { JobApplicationStatus } from "./job_application_status";
import { JobResponse } from "./job_response";
import { JobApplicationMessage } from "./job_application_message";
import { UserDetails } from "./user_details";
import { Review } from "./review";
import { FileData } from "./file_data";

export class JobApplication {
  id?: string;
  userId?: number;
  job?: JobResponse;
  updatedAt: Date;
  status?: JobApplicationStatus;
  userDetails?: UserDetails;
  jobDetails: Job;
  messageList: Set<JobApplicationMessage>;
  review: Review;
  formattedUpdatedAt: string;
  fileList?: FileData[];
}
