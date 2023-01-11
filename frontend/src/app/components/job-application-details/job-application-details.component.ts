import { Component, OnInit, Input } from "@angular/core";
import { JobApplication, User } from "../../models";
import { AccountService, JobApplicationService } from "../../services";
import { ConfirmationService, Message, MessageService } from "primeng/api";
import { first } from "rxjs/operators";

@Component({
  selector: "app-job-application-details",
  templateUrl: "./job-application-details.component.html",
  styleUrls: ["./job-application-details.component.css"],
  providers: [MessageService, ConfirmationService],
})
export class JobApplicationDetailsComponent implements OnInit {
  user?: User | null;

  @Input()
  jobApplication: JobApplication;

  msgs: Message[] = [];

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.accountService.user.subscribe((x) => (this.user = x));
  }

  ngOnInit(): void {}

  onClickWithdraw() {
    this.confirmationService.confirm({
      message: "Do you want to withdraw this job application?",
      header: "Withdraw Confirmation",
      icon: "pi pi-info-circle",
      accept: () => {
        this.jobApplicationService
          .update(this.jobApplication.id, 8)
          .pipe(first())
          .subscribe({
            next: () => {
              this.jobApplication.status.name = "WITHDRAWN";
              this.messageService.add({
                severity: "success",
                summary: "Success",
                detail: "You have withdrawn your job application",
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
