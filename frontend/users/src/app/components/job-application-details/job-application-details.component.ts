import {
  Component,
  OnInit,
  Input,
  LOCALE_ID,
  Inject,
  ViewChild,
  ElementRef,
  AfterViewChecked,
  OnDestroy,
} from "@angular/core";
import {
  FileData,
  JobApplication,
  JobApplicationMessage,
  Review,
  User,
} from "../../models";
import { AccountService, JobApplicationService } from "../../services";
import { ConfirmationService, MessageService } from "primeng/api";
import { first } from "rxjs/operators";
import { formatDate } from "@angular/common";
import { saveAs } from "file-saver";

@Component({
  selector: "app-job-application-details",
  templateUrl: "./job-application-details.component.html",
  styleUrls: ["./job-application-details.component.css"],
  providers: [ConfirmationService],
})
export class JobApplicationDetailsComponent
  implements OnInit, AfterViewChecked, OnDestroy
{
  user?: User | null;

  @ViewChild("scrollMe")
  private myScrollContainer: ElementRef;

  @Input()
  jobApplication: JobApplication;
  fileList?: FileData[];

  displayReviewForm: boolean = false;
  inputRating: number = 0;
  inputDescription: string = "";
  textareaValue: string = "";

  constructor(
    private accountService: AccountService,
    private jobApplicationService: JobApplicationService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    @Inject(LOCALE_ID) private locale: string
  ) {
    this.accountService.user.subscribe((x) => (this.user = x));
  }

  ngOnDestroy(): void {
    this.jobApplicationService.selectedJobApplicationId = null;
  }

  ngOnInit(): void {
    this.jobApplicationService.currentNewMessage.subscribe(
      (msg: JobApplicationMessage) => {
        msg.formattedTimestamp = formatDate(
          msg.timestamp,
          "d MMM y, h:mm:ss a",
          this.locale
        );
        if (
          this.jobApplicationService.selectedJobApplicationId ===
          this.jobApplication.id
        ) {
          this.jobApplication.messageList.add(msg);
        }
      }
    );
    this.scrollToBottom();

    this.jobApplicationService
      .getFileList(this.jobApplication.id)
      .subscribe((result) => {
        this.fileList = result;
      });
  }

  sendMessage() {
    let content = this.textareaValue.trim();
    if (content !== "") {
      this.jobApplicationService
        .addMessage(this.jobApplication.id, {
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
            console.log(this.jobApplication.messageList);
            this.jobApplication.messageList = new Set(
              this.jobApplication.messageList
            );
            this.jobApplication.messageList.add(message);
            this.textareaValue = "";
          },
          error: (error) => {
            console.log("error");
          },
        });
    }
  }

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

  showReviewForm() {
    this.displayReviewForm = true;
    this.inputRating = this.jobApplication.review.rating;
    this.inputDescription = this.jobApplication.review.description;
  }

  saveReview() {
    this.jobApplicationService
      .updateReview(
        this.user.id,
        this.jobApplication.id,
        this.inputRating,
        this.inputDescription
      )
      .pipe(first())
      .subscribe({
        next: (review: Review) => {
          this.displayReviewForm = false;
          this.jobApplication.review = review;
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  downloadFile(fileData: FileData): void {
    this.jobApplicationService
      .downloadFile(this.jobApplication.id, fileData.filename)
      .subscribe((blob) => saveAs(blob, fileData.filename));
  }
}
