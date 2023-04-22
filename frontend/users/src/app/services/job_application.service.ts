import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";

import { environment } from "../../environments/environment";
import {
  FileData,
  JobApplication,
  JobApplicationMessage,
  RecruiterStatistics,
} from "../models";
import { JobApplicationStatus } from "../models/job_application_status";

@Injectable({ providedIn: "root" })
export class JobApplicationService {
  public selectedJobApplicationId: string = null;
  private newMessage = new BehaviorSubject(null);
  currentNewMessage = this.newMessage.asObservable();

  constructor(private http: HttpClient) {}

  updateNewMessage(message: JobApplicationMessage) {
    this.newMessage.next(message);
  }

  create(userId: number, jobId: number) {
    return this.http.post(
      `${environment.jobApplicationsApiURL}/users/${userId}/job-applications`,
      {
        jobId,
      }
    );
  }

  getJobApplicationById(id: string): Observable<JobApplication> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/job-applications/${id}`
    ) as Observable<JobApplication>;
  }

  getJobApplicationsByUserId(userId: number): Observable<JobApplication[]> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/users/${userId}/job-applications`
    ) as Observable<JobApplication[]>;
  }

  getJobApplicationsByJobId(jobId: number): Observable<JobApplication[]> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/jobs/${jobId}/job-applications`
    ) as Observable<JobApplication[]>;
  }

  getStatus(): Observable<JobApplicationStatus[]> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/job-applications/status`
    ) as Observable<JobApplicationStatus[]>;
  }

  getStatusById(statusId: number): Observable<JobApplicationStatus> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/job-applications/status/${statusId}`
    ) as Observable<JobApplicationStatus>;
  }

  getRecruiterStatistics(recruiterId: number): Observable<RecruiterStatistics> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/recruiters/${recruiterId}/statistics`
    ) as Observable<RecruiterStatistics>;
  }

  update(jobApplicationId: string, statusId: number) {
    return this.http.put(
      `${environment.jobApplicationsApiURL}/job-applications/${jobApplicationId}`,
      {
        statusId: statusId,
      }
    );
  }

  addMessage(jobApplicationId: string, message: JobApplicationMessage) {
    return this.http.post(
      `${environment.jobApplicationsApiURL}/job-applications/${jobApplicationId}/messages`,
      message
    );
  }

  updateReview(
    userId: number,
    jobApplicationId: string,
    rating: number,
    description: string
  ) {
    return this.http.put(
      `${environment.jobApplicationsApiURL}/users/${userId}/job-applications/${jobApplicationId}/review`,
      {
        rating,
        description,
      }
    );
  }

  downloadFile(
    jobApplicationId: string,
    filename: string | undefined
  ): Observable<Blob> {
    return this.http.get(
      `${environment.jobApplicationsApiURL}/job-applications/${jobApplicationId}/files/${filename}`,
      {
        responseType: "blob",
      }
    );
  }

  getFileList(jobApplicationId: string): Observable<FileData[]> {
    return this.http.get<FileData[]>(
      `${environment.jobApplicationsApiURL}/job-applications/${jobApplicationId}/files`
    );
  }
}
