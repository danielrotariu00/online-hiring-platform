import { formatDate } from "@angular/common";
import { Component, Inject, LOCALE_ID, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { first } from "rxjs";
import { Notification, User } from "src/app/models";
import { AccountService, JobApplicationService } from "src/app/services";
import { NotificationService } from "src/app/services/notification.service";

@Component({
  selector: "app-notifications",
  templateUrl: "./notifications.component.html",
  styleUrls: ["./notifications.component.css"],
})
export class NotificationsComponent implements OnInit {
  user: User | null;
  notifications: Notification[] = [];

  constructor(
    private accountService: AccountService,
    private notificationService: NotificationService,
    private jobApplicationService: JobApplicationService,
    private router: Router,
    @Inject(LOCALE_ID) private locale: string
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.user = x));

    this.notificationService
      .getUserNotifications(this.user.id)
      .subscribe((notifications) => {
        this.notifications = notifications.map((notification) => {
          notification.formattedTimestamp = formatDate(
            notification.timestamp,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          return notification;
        });
      });
  }

  goToJobApplication(jobApplicationId: number) {
    if (this.user.isRecruiter) {
      this.jobApplicationService
        .getJobApplicationById(jobApplicationId)
        .subscribe((jobApplication) => {
          this.router.navigate(["/recruiter-job-applications"], {
            queryParams: {
              jobId: `${jobApplication.jobId}`,
            },
          });
        });
    } else {
      this.router.navigate(["/job-applications"], {
        queryParams: {
          id: `${jobApplicationId}`,
        },
      });
    }
  }

  deleteNotification(notificationId: number) {
    this.notificationService
      .deleteNotification(notificationId)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.notifications = this.notifications.filter(
            (notification) => notification.id != notificationId
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }
}
