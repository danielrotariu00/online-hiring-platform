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
    private databaseService: DatabaseService
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
      this.company = company;
    });

    this.databaseService
      .getCompanyIndustriesByCompanyId(companyId)
      .subscribe((companyIndustries: CompanyIndustry[]) => {
        this.companyIndustries = companyIndustries.map((companyIndustry) => {
          this.databaseService
            .getIndustryByID(companyIndustry.industryId)
            .subscribe((industry: Industry) => {
              companyIndustry.industry = industry;
            });

          return companyIndustry;
        });
      });

    this.databaseService
      .getCompanyIndustriesFollowedByUser(this.user.id)
      .subscribe((companyIndustries: CompanyIndustry[]) => {
        let ids = companyIndustries.map(
          (companyIndustry) => companyIndustry.id
        );
        this.companyIndustries.forEach((companyIndustry) => {
          companyIndustry.isFollowed = ids.includes(companyIndustry.id);
        });
      });
  }

  onClickFollow(companyIndustry: CompanyIndustry) {
    this.databaseService
      .createCompanyIndustryFollower(this.user.id, companyIndustry.id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          companyIndustry.isFollowed = true;
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  onClickUnfollow(companyIndustry: CompanyIndustry) {
    this.databaseService
      .deleteCompanyIndustryFollower(this.user.id, companyIndustry.id)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          companyIndustry.isFollowed = false;
        },
        error: (error) => {
          console.log("error");
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
