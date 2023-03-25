import { formatDate } from "@angular/common";
import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { JobApplication, User } from "../../models";
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
    let id: string;
    this.route.queryParams.subscribe((params) => {
      id = params.id;
    });

    this.jobApplicationService
      .getJobApplicationsByUserId(this.user.id)
      .subscribe((jobApplications: JobApplication[]) => {
        this.jobApplications = jobApplications.map((jobApplication) => {
          jobApplication.jobDetails = this.databaseService.toJob(
            jobApplication.job
          );

          jobApplication.formattedUpdatedAt = formatDate(
            jobApplication.updatedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          jobApplication.messageList.forEach(
            (message) =>
              (message.formattedTimestamp = formatDate(
                message.timestamp,
                "d MMM y, h:mm:ss a",
                this.locale
              ))
          );

          return jobApplication;
        });

        this.selectedJobApplication = this.jobApplications.find(
          (jobApplications) => jobApplications.id == id
        );
        this.jobApplicationService.selectedJobApplicationId =
          this.selectedJobApplication.id;
      });
  }

  onSelectionChange(event) {
    if (event.value.length === 1) {
      let id = event.value[0].id;

      this.jobApplicationService
        .getJobApplicationById(id)
        .subscribe((jobApplication: JobApplication) => {
          jobApplication.jobDetails = this.databaseService.toJob(
            jobApplication.job
          );

          jobApplication.formattedUpdatedAt = formatDate(
            jobApplication.updatedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          jobApplication.messageList.forEach(
            (message) =>
              (message.formattedTimestamp = formatDate(
                message.timestamp,
                "d MMM y, h:mm:ss a",
                this.locale
              ))
          );

          this.selectedJobApplication = jobApplication;
          console.log(jobApplication);
          this.jobApplicationService.selectedJobApplicationId =
            this.selectedJobApplication.id;
        });
    } else {
      event.value = [this.selectedJobApplication];
    }
  }
}
