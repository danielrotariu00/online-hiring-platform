import { Component, OnInit } from "@angular/core";
import { MenuItem, MessageService } from "primeng/api";
import { distinctUntilChanged, first } from "rxjs";
import { Notification, User } from "src/app/models";
import { NotificationService } from "src/app/services/notification.service";

import {
  AccountService,
  JobApplicationService,
  MenuItemsService,
  WebSocketService,
} from "../../services";

@Component({
  selector: "app-menubar",
  templateUrl: "./menubar.component.html",
  styleUrls: ["./menubar.component.css"],
})
export class MenubarComponent implements OnInit {
  user?: User | null;
  items: MenuItem[] = [];
  unreadNotifications: number = 0;

  constructor(
    private accountService: AccountService,
    private notificationService: NotificationService,
    private jobApplicationService: JobApplicationService,
    private menuItemsService: MenuItemsService,
    private webSocketService: WebSocketService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.accountService.user.subscribe((user) => {
      this.user = user;
      let stompClient = this.webSocketService.connect();
      stompClient.connect({}, (frame) => {
        stompClient.subscribe(`/user/${this.user.id}/notifications`, (msg) => {
          let notification: Notification = JSON.parse(msg.body);
          if (
            notification.jobApplicationId ===
              this.jobApplicationService.selectedJobApplicationId &&
            notification.message != null
          ) {
            this.jobApplicationService.updateNewMessage(notification.message);

            this.notificationService
              .deleteNotification(notification.id)
              .pipe(first())
              .subscribe({
                next: () => {
                  console.log("deleted");
                },
                error: (error) => {
                  console.log("error");
                },
              });
          } else {
            this.messageService.add({
              severity: "info",
              summary: "Notification",
              detail: notification.text,
            });
            this.menuItemsService.menuItems$
              .pipe(distinctUntilChanged())
              .subscribe((menuItems) => {
                menuItems[menuItems.length - 2].badge = String(
                  Number(menuItems[menuItems.length - 2].badge) + 1
                );
                this.menuItemsService.updateMenuItems(menuItems);
              });
          }
        });
      });

      this.notificationService
        .getUserNotifications(this.user.id)
        .subscribe((notifications) => {
          this.unreadNotifications = notifications.filter(
            (notification) => notification.isRead == false
          ).length;

          let regularUserItems: MenuItem[] = [
            {
              label: "Home",
              icon: "pi pi-fw pi-home",
              style: { "margin-left": "auto" },
              routerLink: "/home",
            },
            {
              label: "Companies",
              icon: "pi pi-fw pi-globe",
              routerLink: "/companies",
            },
            {
              label: "Jobs",
              icon: "pi pi-fw pi-briefcase",
              routerLink: "/jobs",
            },
            {
              label: "My Job Applications",
              icon: "pi pi-fw pi-envelope",
              routerLink: "/job-applications",
            },
            {
              label: "Profile",
              icon: "pi pi-fw pi-user",
              routerLink: `/users/${this.user?.id}`,
            },
            {
              label: "Notifications",
              icon: "pi pi-fw pi-bell",
              routerLink: "/notifications",
              badge: `${this.unreadNotifications}`,
            },
            {
              label: "Logout",
              icon: "pi pi-fw pi-sign-out",
              command: () => this.logout(),
            },
          ];

          let recruiterItems: MenuItem[] = [
            {
              label: "Home",
              icon: "pi pi-fw pi-home",
              style: { "margin-left": "auto" },
              routerLink: "/recruiter-home",
            },
            {
              label: "Notifications",
              icon: "pi pi-fw pi-bell",
              routerLink: "/notifications",
              badge: `${this.unreadNotifications}`,
            },
            {
              label: "Logout",
              icon: "pi pi-fw pi-sign-out",
              command: () => this.logout(),
            },
          ];

          if (user.isRecruiter) {
            this.menuItemsService.updateMenuItems(recruiterItems);
          } else {
            this.menuItemsService.updateMenuItems(regularUserItems);
          }
          this.menuItemsService.menuItems$.subscribe(
            (menuItems) => (this.items = menuItems)
          );
        });
    });
  }

  logout() {
    this.accountService.logout();
  }
}
