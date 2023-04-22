import { formatDate } from "@angular/common";
import { isNgTemplate } from "@angular/compiler";
import {
  Component,
  ElementRef,
  Inject,
  LOCALE_ID,
  OnDestroy,
  OnInit,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import {
  MessageService,
  ConfirmationService,
  Message,
  MenuItem,
} from "primeng/api";
import { first } from "rxjs";
import {
  Job,
  JobApplication,
  JobResponse,
  User,
  UserDetails,
  JobApplicationMessage,
} from "../../models";
import { JobApplicationStatus } from "../../models/job_application_status";
import {
  JobApplicationService,
  DatabaseService,
  AccountService,
} from "../../services";

@Component({
  selector: "app-recruiter-job-applications",
  templateUrl: "./recruiter-job-applications.component.html",
  styleUrls: ["./recruiter-job-applications.component.scss"],
  providers: [MessageService, ConfirmationService],
})
export class RecruiterJobApplicationsComponent implements OnInit, OnDestroy {
  user?: User | null;

  @ViewChild("scrollMe")
  private myScrollContainer: ElementRef;

  job: Job;
  jobApplications: JobApplication[];
  selectedJobApplication: JobApplication;

  msgs: Message[] = [];
  statusItems: MenuItem[];

  textareaValue: string;

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService,
    private databaseService: DatabaseService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    @Inject(LOCALE_ID) private locale: string
  ) {}
  ngOnDestroy(): void {
    this.jobApplicationService.selectedJobApplicationId = null;
  }

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.user = x));
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

    this.jobApplicationService.currentNewMessage.subscribe(
      (msg: JobApplicationMessage) => {
        msg.formattedTimestamp = formatDate(
          msg.timestamp,
          "d MMM y, h:mm:ss a",
          this.locale
        );
        if (
          this.jobApplicationService.selectedJobApplicationId ===
          this.selectedJobApplication.id
        ) {
          this.selectedJobApplication.messageList.add(msg);
        }
      }
    );

    let jobId: number;
    let jobApplicationId: string;
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
                .getUserDetails(jobApplication.userId)
                .subscribe((userDetails: UserDetails) => {
                  jobApplication.userDetails = userDetails;
                });

              return jobApplication;
            });
            this.selectedJobApplication = this.jobApplications.find(
              (jobApplications) => jobApplications.id == jobApplicationId
            );
            this.jobApplicationService.selectedJobApplicationId =
              this.selectedJobApplication.id;
            this.selectedJobApplication.formattedUpdatedAt = formatDate(
              this.selectedJobApplication.updatedAt,
              "d MMM y, h:mm:ss a",
              this.locale
            );
            this.selectedJobApplication.messageList.forEach(
              (message) =>
                (message.formattedTimestamp = formatDate(
                  message.timestamp,
                  "d MMM y, h:mm:ss a",
                  this.locale
                ))
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
      let id = event.value[0].id;

      this.jobApplicationService
        .getJobApplicationById(id)
        .subscribe((jobApplication: JobApplication) => {
          this.databaseService
            .getUserDetails(jobApplication.userId)
            .subscribe((userDetails: UserDetails) => {
              jobApplication.userDetails = userDetails;
            });

          jobApplication.jobDetails = this.databaseService.toJob(
            jobApplication.job
          );

          jobApplication.formattedUpdatedAt = formatDate(
            jobApplication.updatedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          this.selectedJobApplication = jobApplication;
          this.selectedJobApplication.messageList.forEach(
            (message) =>
              (message.formattedTimestamp = formatDate(
                message.timestamp,
                "d MMM y, h:mm:ss a",
                this.locale
              ))
          );
          this.scrollToBottom();

          this.jobApplicationService.selectedJobApplicationId =
            this.selectedJobApplication.id;
        });
    } else {
      event.value = [this.selectedJobApplication];
    }
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

  sendMessage() {
    let content = this.textareaValue.trim();
    if (content !== "") {
      this.jobApplicationService
        .addMessage(this.selectedJobApplication.id, {
          userId: this.user.id,
          content: content,
        })
        .pipe(first())
        .subscribe({
          next: (message: JobApplicationMessage) => {
            message.formattedTimestamp = formatDate(
              message.timestamp,
              "d MMM y, h:mm:ss a",
              this.locale
            );
            this.selectedJobApplication.messageList = new Set(
              this.selectedJobApplication.messageList
            );
            this.selectedJobApplication.messageList.add(message);
            this.textareaValue = "";
          },
          error: (error) => {
            console.log("error");
          },
        });
    }
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scroll({
        top: this.myScrollContainer.nativeElement.scrollHeight,
        left: 0,
      });
    } catch (err) {}
  }
}
