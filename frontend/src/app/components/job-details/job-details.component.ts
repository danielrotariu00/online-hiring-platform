import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { Job, JobApplication, JobResponse, User } from "../../models";
import { AccountService, JobApplicationService } from "../../services";
import { first } from "rxjs/operators";

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
    private jobApplicationService: JobApplicationService
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
          console.log("success");
          this.applied = true;
        },
        error: (error) => {
          console.log("error");
        },
      });
  }
}
