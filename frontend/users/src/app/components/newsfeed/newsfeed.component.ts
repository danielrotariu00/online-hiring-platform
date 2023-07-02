import { formatDate } from "@angular/common";
import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
import { Job, NewsfeedEntry, User } from "src/app/models";
import { AccountService, DatabaseService } from "src/app/services";
import { NewsfeedService } from "src/app/services/newsfeed.service";

@Component({
  selector: "app-newsfeed",
  templateUrl: "./newsfeed.component.html",
  styleUrls: ["./newsfeed.component.scss"],
})
export class NewsfeedComponent implements OnInit {
  jobs: Job[] = [];
  selectedJob: Job;
  entries: NewsfeedEntry[] = [];
  user: User | null;
  maxEntries: number = 6;

  constructor(
    private databaseService: DatabaseService,
    private newsfeedService: NewsfeedService,
    private accountService: AccountService,
    @Inject(LOCALE_ID) private locale: string
  ) {
    this.user = this.accountService.userValue;
  }

  ngOnInit(): void {
    this.newsfeedService
      .getNewsfeed(this.user.id, this.maxEntries)
      .subscribe((entries: NewsfeedEntry[]) => {
        entries.map((entry) => {
          this.databaseService
            .getJobById(entry.jobId)
            .subscribe((jobResponse) => {
              let job = this.databaseService.toJob(jobResponse);
              this.jobs.push(...[job]);
              this.jobs = this.jobs.map((job) => {
                job.formattedTimestamp = formatDate(
                  job.postedAt,
                  "d MMM y, h:mm:ss a",
                  this.locale
                );

                return job;
              });
              this.selectedJob = this.jobs[0];
            });
        });
      });
  }

  onScroll(event: any): void {
    if (
      event.target.offsetHeight + event.target.scrollTop >=
      event.target.scrollHeight - 1
    ) {
      this.newsfeedService
        .getNewsfeed(this.user.id, this.maxEntries)
        .subscribe((entries: NewsfeedEntry[]) => {
          entries.map((entry) => {
            this.databaseService
              .getJobById(entry.jobId)
              .subscribe((jobResponse) => {
                let job = this.databaseService.toJob(jobResponse);
                this.jobs.push(...[job]);
                this.jobs = this.jobs.map((job) => {
                  job.formattedTimestamp = formatDate(
                    job.postedAt,
                    "d MMM y, h:mm:ss a",
                    this.locale
                  );

                  return job;
                });
              });
          });
        });
    }
  }

  setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }
}
