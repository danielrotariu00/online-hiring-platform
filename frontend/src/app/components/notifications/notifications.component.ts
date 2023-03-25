import { formatDate } from "@angular/common";
import {
  Component,
  Inject,
  LOCALE_ID,
  OnInit,
  ViewEncapsulation,
} from "@angular/core";
import { Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { distinctUntilChanged, first } from "rxjs";
import { Notification, User } from "src/app/models";
import {
  AccountService,
  JobApplicationService,
  MenuItemsService,
} from "src/app/services";
import { NotificationService } from "src/app/services/notification.service";

@Component({
  selector: "app-notifications",
  templateUrl: "./notifications.component.html",
  styleUrls: ["./notifications.component.css"],
  encapsulation: ViewEncapsulation.None,
})
export class NotificationsComponent implements OnInit {
  user: User | null;
  notifications: Notification[] = [];

  constructor(
    private accountService: AccountService,
    private notificationService: NotificationService,
    private jobApplicationService: JobApplicationService,
    private menuItemsService: MenuItemsService,
    private messageService: MessageService,
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

  goToJobApplication(notification: Notification) {
    if (this.user.isRecruiter) {
      this.jobApplicationService
        .getJobApplicationById(notification.jobApplicationId)
        .subscribe((jobApplication) => {
          this.router.navigate(["/recruiter-job-applications"], {
            queryParams: {
              jobId: `${jobApplication.job.id}`,
              jobApplicationId: `${jobApplication.id}`,
            },
          });
        });
    } else {
      this.router.navigate(["/job-applications"], {
        queryParams: {
          id: `${notification.jobApplicationId}`,
        },
      });
    }
    if (!notification.isRead) {
      this.notificationService
        .markNotificationAsRead(notification.id)
        .pipe(first())
        .subscribe({
          next: () => {
            this.menuItemsService.menuItems$
              .pipe(distinctUntilChanged())
              .subscribe((menuItems) => {
                menuItems[menuItems.length - 2].badge = String(
                  Number(menuItems[menuItems.length - 2].badge) - 1
                );
                this.menuItemsService.updateMenuItems(menuItems);
              });
          },
          error: (error) => {},
        });
    }
  }

  deleteNotification(notification: Notification) {
    let notificationId = notification.id;
    this.notificationService
      .deleteNotification(notificationId)
      .pipe(first())
      .subscribe({
        next: () => {
          if (!notification.isRead) {
            this.menuItemsService.menuItems$
              .pipe(distinctUntilChanged())
              .subscribe((menuItems) => {
                menuItems[menuItems.length - 2].badge = String(
                  Number(menuItems[menuItems.length - 2].badge) - 1
                );
                this.menuItemsService.updateMenuItems(menuItems);
              });
          }
          this.messageService.add({
            severity: "success",
            summary: "Succes",
            detail: "Notification deleted succesfully.",
          });
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
