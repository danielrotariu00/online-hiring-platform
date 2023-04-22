import { JobResponse } from "./job_response";

export interface JobQueryResponse {
  jobList: JobResponse[];
  totalElements: number;
  totalPages: number;
}
