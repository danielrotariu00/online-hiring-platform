import { Component, OnInit } from "@angular/core";
import { JobApplication, JobResponse, User } from "../models";
import { JobApplicationStatus } from "../models/job_application_status";
import {
  AccountService,
  JobApplicationService,
  DatabaseService,
} from "../services";

@Component({
  selector: "app-job-applications",
  templateUrl: "./job-applications.component.html",
  styleUrls: ["./job-applications.component.scss"],
})
export class JobApplicationsComponent implements OnInit {
  user?: User | null;
  jobApplications: JobApplication[];
  selectedJobApplication: JobApplication;

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService,
    private databaseService: DatabaseService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.user = x));

    this.jobApplicationService
      .getJobApplicationsByUserId(this.user.id)
      .subscribe((jobApplications: JobApplication[]) => {
        this.jobApplications = jobApplications.map((jobApplication) => {
          this.databaseService
            .getJobById(jobApplication.jobId)
            .subscribe((jobResponse: JobResponse) => {
              jobApplication.job = this.databaseService.toJob(jobResponse);
            });

          this.jobApplicationService
            .getStatusById(jobApplication.statusId)
            .subscribe((status: JobApplicationStatus) => {
              jobApplication.status = status;
            });

          return jobApplication;
        });

        this.selectedJobApplication = this.jobApplications[0];
      });
  }

  onSelectionChange(event) {
    if (event.value.length === 1) {
      this.selectedJobApplication = event.value[0];
    } else {
      event.value = [this.selectedJobApplication];
    }
    console.log(this.selectedJobApplication.job.title);
  }
}
