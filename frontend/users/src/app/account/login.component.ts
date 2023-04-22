import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs/operators";

import { AccountService, AlertService } from "../services";
import { User } from "../models";

@Component({ templateUrl: "login.component.html" })
export class LoginComponent implements OnInit {
  loggedUser?: User | null;

  form!: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.accountService.user.subscribe((x) => (this.loggedUser = x));
    this.form = this.formBuilder.group({
      email: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.form.controls;
  }

  onSubmit() {
    this.submitted = true;

    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }

    this.loading = true;
    this.accountService
      .login(this.f.email.value, this.f.password.value)
      .pipe(first())
      .subscribe({
        next: () => {
          this.loading = false;
          if (this.loggedUser.isManager) {
            this.router.navigate([`manager-home`]);
          } else if (this.loggedUser.isRecruiter) {
            this.router.navigate([`recruiter-home`]);
          } else if (this.loggedUser.isCandidate) {
            this.router.navigate([`home`]);
          } else {
            this.accountService.logout();
            this.alertService.error("UNAUTHORIZED");
          }
        },
        error: (error) => {
          this.alertService.error(error);
          this.loading = false;
        },
      });
  }
}
