import { Component, OnInit } from "@angular/core";

import { AccountService } from "./services";
import { User } from "./models";
import { PrimeNGConfig } from "primeng/api";

@Component({ selector: "app-root", templateUrl: "app.component.html" })
export class AppComponent implements OnInit {
  user?: User | null;

  constructor(
    private primengConfig: PrimeNGConfig,
    private accountService: AccountService
  ) {
    this.accountService.user.subscribe((x) => (this.user = x));
  }
  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }

  logout() {
    this.accountService.logout();
  }
}
