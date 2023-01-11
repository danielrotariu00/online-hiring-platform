import { Component, OnInit } from "@angular/core";
import { MenuItem } from "primeng/api";
import { User } from "src/app/models";
import { NotificationService } from "src/app/services/notification.service";

import { AccountService } from "../../services";

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
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.accountService.user.subscribe((user) => {
      this.user = user;

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
              label: "Notifications",
              icon: "pi pi-fw pi-bell",
              routerLink: "/notifications",
              badge: `${this.unreadNotifications}`,
            },
            {
              label: "Profile",
              icon: "pi pi-fw pi-user",
              routerLink: `/users/${this.user?.id}`,
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
            this.items = recruiterItems;
          } else {
            this.items = regularUserItems;
          }
        });
    });
  }

  logout() {
    this.accountService.logout();
  }
}
