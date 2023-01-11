import { isNgTemplate } from "@angular/compiler";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import {
  MessageService,
  ConfirmationService,
  Message,
  MenuItem,
} from "primeng/api";
import { first } from "rxjs";
import { Job, JobApplication, JobResponse, UserDetails } from "../../models";
import { JobApplicationStatus } from "../../models/job_application_status";
import { JobApplicationService, DatabaseService } from "../../services";

@Component({
  selector: "app-recruiter-job-applications",
  templateUrl: "./recruiter-job-applications.component.html",
  styleUrls: ["./recruiter-job-applications.component.scss"],
  providers: [MessageService, ConfirmationService],
})
export class RecruiterJobApplicationsComponent implements OnInit {
  job: Job;
  jobApplications: JobApplication[];
  selectedJobApplication: JobApplication;

  msgs: Message[] = [];
  statusItems: MenuItem[];

  constructor(
    private jobApplicationService: JobApplicationService,
    private databaseService: DatabaseService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.statusItems = [
      {
        label: "Update",
        command: () => {
          // this.update();
        },
      },
      {
        label: "Delete",
        command: () => {
          // this.delete();
        },
      },
    ];

    let jobId: number;
    let jobApplicationId: number;
    this.route.queryParams.subscribe((params) => {
      jobId = params.jobId;
      jobApplicationId = params.jobApplicationId;
    });

    this.databaseService
      .getJobById(jobId)
      .subscribe((jobResponse: JobResponse) => {
        this.job = this.databaseService.toJob(jobResponse);

        this.jobApplicationService
          .getJobApplicationsByJobId(this.job.id)
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

              this.databaseService
                .getUserDetails(jobApplication.userId)
                .subscribe((userDetails: UserDetails) => {
                  jobApplication.userDetails = userDetails;
                });

              return jobApplication;
            });

            this.selectedJobApplication = this.jobApplications.find(
              (jobApplications) => jobApplications.id == jobApplicationId
            );
          });
      });

    this.jobApplicationService
      .getStatus()
      .subscribe((status: JobApplicationStatus[]) => {
        this.statusItems = status
          .filter((status) => status.id != 1 && status.id != 8)
          .map((status) => {
            let item: MenuItem = {
              label: `${status.name}`,
              command: () => {
                this.changeStatus(status);
              },
            };

            return item;
          });
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

  viewProfile() {
    this.router.navigate([`users/${this.selectedJobApplication.userId}`]);
  }

  changeStatus(status) {
    this.confirmationService.confirm({
      message: `Are you sure you want to change the status of this job application to ${status.name}?`,
      header: "Status Change Confirmation",
      icon: "pi pi-info-circle",
      accept: () => {
        this.jobApplicationService
          .update(this.selectedJobApplication.id, status.id)
          .pipe(first())
          .subscribe({
            next: () => {
              this.selectedJobApplication.status.name = status.name;
              this.messageService.add({
                severity: "success",
                summary: "Success",
                detail: "You have changed the status of this application",
              });
            },
            error: (error) => {
              this.messageService.add({
                severity: "error",
                summary: "Error",
                detail: "An error has occured",
              });
            },
          });
      },
    });
  }
}
