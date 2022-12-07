import { Component } from "@angular/core";
import { MenuItem } from "primeng/api";

import { AccountService } from "../_services";

@Component({
  selector: "app-menubar",
  templateUrl: "./menubar.component.html",
  styleUrls: ["./menubar.component.css"],
})
export class MenubarComponent {
  items: MenuItem[] = [];

  ngOnInit() {
    this.items = [
      {
        label: "Home",
        icon: "pi pi-fw pi-home",
        style: { "margin-left": "auto" },
        routerLink: "/home",
      },
      {
        label: "Jobs",
        icon: "pi pi-fw pi-briefcase",
        routerLink: "/jobs",
      },
      {
        label: "My Job Applications",
        icon: "pi pi-fw pi-envelope",
      },
      {
        label: "Notifications",
        icon: "pi pi-fw pi-bell",
      },
      {
        label: "My profile",
        icon: "pi pi-fw pi-user",
        routerLink: "/users",
      },
      {
        label: "Logout",
        icon: "pi pi-fw pi-sign-out",
        command: () => this.logout(),
      },
    ];
  }

  constructor(private accountService: AccountService) {}

  logout() {
    this.accountService.logout();
  }
}
