import { Component, OnInit, Input } from "@angular/core";
import { Job, User } from "../models";
import { AccountService, JobApplicationService } from "../services";
import { first } from "rxjs/operators";

@Component({
  selector: "app-job-details",
  templateUrl: "./job-details.component.html",
  styleUrls: ["./job-details.component.css"],
})
export class JobDetailsComponent implements OnInit {
  user?: User | null;
  @Input()
  job: Job;

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService
  ) {
    this.accountService.user.subscribe((x) => (this.user = x));
  }

  ngOnInit(): void {}

  onClickApply() {
    this.jobApplicationService
      .create(this.user.id, this.job.id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
        },
        error: (error) => {
          console.log("error");
        },
      });
  }
}
