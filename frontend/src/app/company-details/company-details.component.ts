import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { City, Company, CompanyIndustry, Country, Industry } from "../models";
import { DatabaseService } from "../services";

@Component({
  selector: "app-company-details",
  templateUrl: "./company-details.component.html",
  styleUrls: ["./company-details.component.scss"],
})
export class CompanyDetailsComponent implements OnInit {
  company: Company | undefined;
  companyIndustries: CompanyIndustry[];
  selectedCompanyIndustry: CompanyIndustry;

  constructor(
    private route: ActivatedRoute,
    private databaseService: DatabaseService
  ) {}

  ngOnInit(): void {
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
  }

  onClickFollow() {}

  onClickViewJobs() {}
}
