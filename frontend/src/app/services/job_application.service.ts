import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";

import { environment } from "../../environments/environment";
import { JobApplication } from "../models";
import { JobApplicationStatus } from "../models/job_application_status";

@Injectable({ providedIn: "root" })
export class JobApplicationService {
  constructor(private http: HttpClient) {}

  create(userId: number, jobId: number) {
    return this.http.post(
      `${environment.jobApplicationApiURL}/job-applications`,
      {
        userId,
        jobId,
      }
    );
  }

  getJobApplicationById(id: number): Observable<JobApplication> {
    return this.http.get(
      `${environment.jobApplicationApiURL}/job-applications/${id}`
    ) as Observable<JobApplication>;
  }

  getJobApplicationsByUserId(userId: number): Observable<JobApplication[]> {
    return this.http.get(
      `${environment.jobApplicationApiURL}/users/${userId}/job-applications`
    ) as Observable<JobApplication[]>;
  }

  getJobApplicationsByJobId(jobId: number): Observable<JobApplication[]> {
    return this.http.get(
      `${environment.jobApplicationApiURL}/jobs/${jobId}/job-applications`
    ) as Observable<JobApplication[]>;
  }

  getStatus(): Observable<JobApplicationStatus[]> {
    return this.http.get(
      `${environment.jobApplicationApiURL}/job-applications/status`
    ) as Observable<JobApplicationStatus[]>;
  }

  getStatusById(statusId: number): Observable<JobApplicationStatus> {
    return this.http.get(
      `${environment.jobApplicationApiURL}/job-applications/status/${statusId}`
    ) as Observable<JobApplicationStatus>;
  }

  update(jobApplicationId: number, statusId: number) {
    return this.http.put(
      `${environment.jobApplicationApiURL}/job-applications/${jobApplicationId}`,
      {
        statusId: statusId,
      }
    );
  }
}
