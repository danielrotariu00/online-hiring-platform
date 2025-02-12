import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs/operators";

import { AccountService, AlertService, DatabaseService } from "../services";
import { User } from "../models";

@Component({ templateUrl: "register.component.html" })
export class RegisterComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  submitted = false;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private databaseSerivce: DatabaseService,
    private alertService: AlertService
  ) {}

  ngOnInit() {
    this.form = this.formBuilder.group({
      email: ["", [Validators.required, Validators.email]],
      password: ["", [Validators.required, Validators.minLength(6)]],
      firstName: [
        "",
        Validators.required,
        Validators.pattern("/^[a-zA-Zs-]*$/"),
      ],
      lastName: [
        "",
        Validators.required,
        Validators.pattern("/^[a-zA-Zs-]*$/"),
      ],
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
      .register(
        this.form.get("email").value,
        this.form.get("password").value,
        this.form.get("firstName").value,
        this.form.get("lastName").value
      )
      .pipe(first())
      .subscribe({
        next: (user: User) => {
          this.alertService.success("Registration successful", {
            keepAfterRouteChange: true,
          });
          this.router.navigate(["../login"], { relativeTo: this.route });
        },
        error: (error) => {
          this.alertService.error(error);
          this.loading = false;
        },
      });
  }
}
