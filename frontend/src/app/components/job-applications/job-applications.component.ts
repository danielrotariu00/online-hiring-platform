import { formatDate } from "@angular/common";
import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { JobApplication, JobResponse, User } from "../../models";
import { JobApplicationStatus } from "../../models/job_application_status";
import {
  AccountService,
  JobApplicationService,
  DatabaseService,
} from "../../services";

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
    private databaseService: DatabaseService,
    private route: ActivatedRoute,
    @Inject(LOCALE_ID) private locale: string
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.user = x));
    let id: number;
    this.route.queryParams.subscribe((params) => {
      id = params.id;
    });

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

          jobApplication.formattedUpdatedAt = formatDate(
            jobApplication.updatedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          return jobApplication;
        });

        this.selectedJobApplication = this.jobApplications.find(
          (jobApplications) => jobApplications.id == id
        );
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
