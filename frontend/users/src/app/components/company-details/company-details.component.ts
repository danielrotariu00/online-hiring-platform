import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Router } from "@angular/router";
import { first } from "rxjs";
import {
  City,
  Company,
  CompanyIndustry,
  Country,
  Industry,
  User,
} from "../../models";
import { AccountService, DatabaseService } from "../../services";
import { MessageService } from "primeng/api";

@Component({
  selector: "app-company-details",
  templateUrl: "./company-details.component.html",
  styleUrls: ["./company-details.component.scss"],
})
export class CompanyDetailsComponent implements OnInit {
  user?: User | null;

  company: Company | undefined;
  companyIndustries: CompanyIndustry[];
  selectedCompanyIndustry: CompanyIndustry;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private accountService: AccountService,
    private databaseService: DatabaseService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.user = x));
    const companyId = parseInt(this.route.snapshot.paramMap.get("id")!, 10);

    this.databaseService.getCompanyById(companyId).subscribe((company) => {
      this.databaseService
        .getCityById(company.cityId)
        .subscribe((city: City) => {
          company.city = city;

          this.databaseService
            .getCountryById(company.city.countryId)
            .subscribe((country: Country) => {
              company.country = country;
            });
        });

      let compIndustries = [];
      this.databaseService
        .getCompanyIndustriesByCompanyId(companyId)
        .subscribe((companyIndustries: CompanyIndustry[]) => {
          compIndustries = companyIndustries.map((companyIndustry) => {
            this.databaseService
              .getIndustryByID(companyIndustry.industryId)
              .subscribe((industry: Industry) => {
                companyIndustry.industry = industry;
              });

            return companyIndustry;
          });

          this.databaseService
            .getCompanyIndustriesFollowedByUser(this.user.id)
            .subscribe((companyIndustries: CompanyIndustry[]) => {
              let ids = companyIndustries.map(
                (companyIndustry) => companyIndustry.id
              );

              let temp = [];

              compIndustries.forEach((companyIndustry) => {
                companyIndustry.isFollowed = ids.includes(companyIndustry.id);
                temp.push(companyIndustry);
              });

              this.companyIndustries = [...temp];

              if (company.photo) {
                let splitted = company.photo.split(":");
                splitted[1] = "//localhost";
                company.photo = splitted.join(":") + `?${Date.now()}`;
              }

              this.company = company;
            });
        });
    });
  }

  onClickFollow(companyIndustry: CompanyIndustry) {
    this.databaseService
      .createCompanyIndustryFollower(this.user.id, companyIndustry.id)
      .pipe(first())
      .subscribe({
        next: () => {
          companyIndustry.isFollowed = true;
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Industry followed successfully.",
          });
        },
        error: (error) => {
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: "An error has occured.",
          });
        },
      });
  }

  onClickUnfollow(companyIndustry: CompanyIndustry) {
    this.databaseService
      .deleteCompanyIndustryFollower(this.user.id, companyIndustry.id)
      .pipe(first())
      .subscribe({
        next: () => {
          companyIndustry.isFollowed = false;
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Industry unfollowed successfully.",
          });
        },
        error: (error) => {
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: "An error has occured.",
          });
        },
      });
  }

  onClickViewJobs(industryId: number) {
    this.router.navigate(["/jobs"], {
      queryParams: {
        companyId: `${this.company.id}`,
        industryId: `${industryId}`,
      },
    });
  }
}
