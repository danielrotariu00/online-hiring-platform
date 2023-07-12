import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { Job, JobApplication, JobResponse, User } from "../../models";
import { AccountService, JobApplicationService } from "../../services";
import { first } from "rxjs/operators";
import { MessageService } from "primeng/api";

@Component({
  selector: "app-job-details",
  templateUrl: "./job-details.component.html",
  styleUrls: ["./job-details.component.css"],
})
export class JobDetailsComponent implements OnChanges {
  user?: User | null;

  @Input()
  job: Job;

  applied: boolean = false;

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService,
    private messageService: MessageService
  ) {
    this.accountService.user.subscribe((x) => (this.user = x));
  }

  ngOnChanges() {
    this.jobApplicationService
      .getJobApplicationsByUserId(this.user.id)
      .subscribe((jobApplications: JobApplication[]) => {
        let jobIds = jobApplications.map(
          (jobApplication) => jobApplication.job.id
        );
        this.applied = jobIds.includes(this.job.id);
      });
  }

  onClickApply() {
    this.jobApplicationService
      .create(this.user.id, this.job.id)
      .pipe(first())
      .subscribe({
        next: () => {
          this.applied = true;
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Application sent successfully.",
          });
        },
        error: (error) => {
          this.messageService.add({
            severity: "error",
            summary: "error",
            detail: "An error has occured.",
          });
        },
      });
  }
}
