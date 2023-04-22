import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { first, map } from "rxjs/operators";

import { environment } from "../../environments/environment";
import { User } from "../models";

@Injectable({ providedIn: "root" })
export class AccountService {
  private userSubject: BehaviorSubject<User | null>;
  public user: Observable<User | null>;

  constructor(private router: Router, private http: HttpClient) {
    this.userSubject = new BehaviorSubject(
      JSON.parse(localStorage.getItem("user")!)
    );
    this.user = this.userSubject.asObservable();
  }

  public get userValue() {
    return this.userSubject.value;
  }

  login(email: string, password: string) {
    return this.http
      .post<User>(`${environment.apiUrl}/login`, {
        email,
        password,
      })
      .pipe(
        map((user: User) => {
          // store user details and jwt token in local storage to keep user logged in between page refreshes
          let candidateId = 1;
          let recruiterId = 2;
          let managerId = 3;

          let userRole = user.userRole;
          if (userRole.roleId === candidateId) {
            user.isCandidate = true;
          } else {
            user.isCandidate = false;
          }

          if (userRole.roleId === recruiterId) {
            user.isRecruiter = true;
          } else {
            user.isRecruiter = false;
          }

          if (userRole.roleId === managerId) {
            user.isManager = true;
          } else {
            user.isManager = false;
          }

          user.companyId = userRole.companyId;

          localStorage.setItem("user", JSON.stringify(user));
          this.userSubject.next(user);
          return user;
        })
      );
  }

  logout() {
    this.http
      .post(`${environment.apiUrl}/logout`, {
        token: this.userValue.token,
      })
      .pipe(first())
      .subscribe({
        next: () => {
          localStorage.removeItem("user");
          this.userSubject.next(null);
          this.router.navigate(["/account/login"]);
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  register(
    email: string,
    password: string,
    firstName: string,
    lastName: string
  ) {
    return this.http.post(`${environment.apiUrl}/register`, {
      email,
      password,
      firstName,
      lastName,
    });
  }
}
