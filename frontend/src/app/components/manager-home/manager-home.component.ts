import { Component, OnInit, ViewChild } from "@angular/core";
import {
  City,
  Company,
  CompanyIndustry,
  Country,
  Industry,
  User,
} from "../../models";
import { AccountService, DatabaseService } from "../../services";
import { Router } from "@angular/router";
import { first } from "rxjs";
import { FileUpload } from "primeng/fileupload";

@Component({
  selector: "app-manager-home",
  templateUrl: "./manager-home.component.html",
  styleUrls: ["./manager-home.component.scss"],
})
export class ManagerHomeComponent implements OnInit {
  loggedUser?: User | null;

  //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  companyId: number = 4;
  company: Company;
  companyIndustries: CompanyIndustry[];

  displayIndustryForm: boolean = false;
  industries: Industry[];
  selectedIndustry: Industry;

  displayCompanyDetailsForm: boolean = false;
  companyName: string;
  companyWebsite: string;
  countries: Country[];
  selectedCountry: Country;
  cities: City[];
  selectedCity: City;
  companyDescription: string;
  @ViewChild(FileUpload) imageUpload!: FileUpload;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private databaseService: DatabaseService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => (this.loggedUser = x));

    this.databaseService.getCompanyById(this.companyId).subscribe((company) => {
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
      .getCompanyIndustriesByCompanyId(this.companyId)
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

  showIndustryForm() {
    this.displayIndustryForm = true;
    this.databaseService.getIndustries().subscribe((industries: Industry[]) => {
      this.industries = industries;
    });
  }

  addCompanyIndustry() {
    this.databaseService
      .addCompanyIndustry(this.companyId, this.selectedIndustry.id)
      .pipe(first())
      .subscribe({
        next: (companyIndustry: CompanyIndustry) => {
          console.log(companyIndustry);
          this.displayIndustryForm = false;
          this.databaseService
            .getIndustryByID(companyIndustry.industryId)
            .subscribe((industry: Industry) => {
              companyIndustry.industry = industry;
            });
          this.companyIndustries.push(...[companyIndustry]);
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  deleteCompanyIndustry(industryId: number) {
    this.databaseService
      .deleteCompanyIndustry(this.companyId, industryId)
      .pipe(first())
      .subscribe({
        next: () => {
          console.log("success");
          this.companyIndustries = this.companyIndustries.filter(
            (companyIndustry) => companyIndustry.industryId != industryId
          );
        },
        error: (error) => {
          console.log("error");
        },
      });
  }

  showCompanyDetailsForm() {
    this.displayCompanyDetailsForm = true;
    this.databaseService.getCountries().subscribe((countries: Country[]) => {
      this.countries = countries;
    });
    this.selectedCountry = this.company.country;
    this.databaseService
      .getCitiesByCountryId(this.selectedCountry.id)
      .subscribe((cities: City[]) => {
        this.cities = cities;
        this.selectedCity = this.company.city;
      });
    this.companyName = this.company.name;
    this.companyWebsite = this.company.website;
    this.companyDescription = this.company.description;
  }

  saveCompanyDetails() {
    this.databaseService
      .updateCompany(
        this.company.id,
        this.companyName,
        this.companyWebsite,
        this.selectedCity.id,
        this.companyDescription,
        this.company.photo
      )
      .pipe(first())
      .subscribe({
        next: (company: Company) => {
          this.displayCompanyDetailsForm = false;
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
          this.company.name = company.name;
          this.company = company;
        },
        error: (error) => {
          console.log("error");
        },
      });

    if (this.imageUpload.hasFiles()) {
      this.databaseService
        .uploadCompanyImage(this.company.id, this.imageUpload)
        .pipe(first())
        .subscribe({
          next: (event: any) => {
            console.log("success");
          },
          error: (error) => {
            console.log("error");
          },
        });
    }
  }

  onChangeCountry(event: any) {
    this.cities = [];

    if (this.selectedCountry) {
      this.databaseService
        .getCitiesByCountryId(this.selectedCountry.id)
        .subscribe((cities: City[]) => {
          this.cities.push(...cities);
        });
    } else {
    }
  }
}
