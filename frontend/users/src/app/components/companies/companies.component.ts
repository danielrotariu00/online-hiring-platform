import { Component, OnInit } from "@angular/core";
import { City, Company, Country, Industry } from "../../models";
import { DatabaseService } from "../../services";
import { Router } from "@angular/router";

@Component({
  selector: "app-companies",
  templateUrl: "./companies.component.html",
  styleUrls: ["./companies.component.scss"],
})
export class CompaniesComponent implements OnInit {
  industries: Industry[];
  companies: Company[];

  constructor(
    private router: Router,
    private databaseService: DatabaseService
  ) {}

  ngOnInit(): void {
    this.databaseService.getIndustries().subscribe((industries: Industry[]) => {
      this.industries = industries;
    });

    this.databaseService.getCompanies().subscribe((companies: Company[]) => {
      this.companies = companies.map((company) => {
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

        return company;
      });
    });
  }

  onIndustrySelectionChange(event) {
    if (event.value.length === 1) {
      this.databaseService
        .getCompaniesByIndustryId(event.value[0].id)
        .subscribe((companies: Company[]) => {
          this.companies = companies.map((company) => {
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

            return company;
          });
        });
    }
  }

  onCompanySelectionChange(event) {
    if (event.value.length === 1) {
      this.router.navigate([`companies/${event.value[0].id}`]);
    }
  }
}
