import { Component, OnInit } from "@angular/core";
import { CompanyRecruiter, RecruiterStatistics, User } from "../../models";
import {
  AccountService,
  DatabaseService,
  JobApplicationService,
} from "../../services";
import { Router } from "@angular/router";
import { first } from "rxjs";

@Component({
  selector: "app-recruiters",
  templateUrl: "./company-recruiters.component.html",
  styleUrls: ["./company-recruiters.component.scss"],
})
export class CompanyRecruitersComponent implements OnInit {
  loggedUser?: User | null;

  companyRecruiters: CompanyRecruiter[];
  selectedCompanyRecruiter: CompanyRecruiter;
  recruiterStatistics: RecruiterStatistics;

  companyId: number;

  displayRecruiterForm: boolean = false;
  email: string;
  password: string;

  constructor(
    private accountService: AccountService,
    private databaseService: DatabaseService,
    private jobApplicationService: JobApplicationService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => {
      this.loggedUser = x;
      this.companyId = this.loggedUser.companyId;

      this.databaseService
        .getCompanyRecruitersByCompanyId(this.companyId)
        .subscribe((companyRecruiters: CompanyRecruiter[]) => {
          this.companyRecruiters = companyRecruiters.map((companyRecruiter) => {
            this.databaseService
              .getRecruiterById(companyRecruiter.recruiterId)
              .subscribe((companyRecruiterResponse: CompanyRecruiter) => {
                companyRecruiter.email = companyRecruiterResponse.email;
              });

            return companyRecruiter;
          });
        });
    });
  }

  onRecruiterSelectionChange(event) {
    let recruiterId = event.value[0]?.recruiterId;
    this.selectedCompanyRecruiter = this.companyRecruiters.find(
      (companyRecruiter) => companyRecruiter?.recruiterId == recruiterId
    );
    this.jobApplicationService
      .getRecruiterStatistics(recruiterId)
      .subscribe((recruiterStatistics: RecruiterStatistics) => {
        this.recruiterStatistics = recruiterStatistics;
      });
  }

  showRecruiterForm() {
    this.displayRecruiterForm = true;
  }

  addRecruiter() {
    this.databaseService
      .addCompanyRecruiter(this.companyId, this.email, this.password)
      .pipe(first())
      .subscribe({
        next: () => {
          this.displayRecruiterForm = false;
          this.databaseService
            .getCompanyRecruitersByCompanyId(this.companyId)
            .subscribe((companyRecruiters: CompanyRecruiter[]) => {
              this.companyRecruiters = companyRecruiters.map(
                (companyRecruiter) => {
                  this.databaseService
                    .getRecruiterById(companyRecruiter.recruiterId)
                    .subscribe((companyRecruiterResponse: CompanyRecruiter) => {
                      companyRecruiter.email = companyRecruiterResponse.email;
                    });

                  return companyRecruiter;
                }
              );
            });
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteRecruiter() {
    this.databaseService
      .deleteCompanyRecruiter(
        this.companyId,
        this.selectedCompanyRecruiter.recruiterId
      )
      .pipe(first())
      .subscribe({
        next: () => {
          this.companyRecruiters = this.companyRecruiters.filter(
            (companyRecruiter) =>
              companyRecruiter.recruiterId !=
              this.selectedCompanyRecruiter.recruiterId
          );
          this.selectedCompanyRecruiter = null;
        },
        error: (error) => {
          console.log("error");
        },
      });
  }
}
