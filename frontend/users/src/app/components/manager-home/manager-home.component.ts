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
import { MessageService } from "primeng/api";

@Component({
  selector: "app-manager-home",
  templateUrl: "./manager-home.component.html",
  styleUrls: ["./manager-home.component.scss"],
})
export class ManagerHomeComponent implements OnInit {
  loggedUser?: User | null;

  companyId: number;
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
    private accountService: AccountService,
    private databaseService: DatabaseService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => {
      this.loggedUser = x;
      this.companyId = this.loggedUser.companyId;

      this.databaseService
        .getCompanyById(this.companyId)
        .subscribe((company) => {
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

          if (company.photo) {
            let splitted = company.photo.split(":");
            splitted[1] = "//localhost";
            company.photo = splitted.join(":") + `?${Date.now()}`;

            // company.photo = company.photo + `?${Date.now()}`;
          }

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
    });
  }

  showIndustryForm() {
    this.displayIndustryForm = true;
    this.industries = [];

    this.databaseService.getIndustries().subscribe((industries: Industry[]) => {
      let companyIndustryIds = this.companyIndustries.map(
        (companyIndustry) => companyIndustry.industryId
      );

      for (let i = 0; i < industries.length; i++) {
        if (!companyIndustryIds.includes(industries[i].id)) {
          this.industries.push(industries[i]);
        }
      }
    });
  }

  addCompanyIndustry() {
    this.databaseService
      .addCompanyIndustry(this.companyId, this.selectedIndustry.id)
      .pipe(first())
      .subscribe({
        next: (companyIndustry: CompanyIndustry) => {
          this.displayIndustryForm = false;
          this.databaseService
            .getIndustryByID(companyIndustry.industryId)
            .subscribe((industry: Industry) => {
              companyIndustry.industry = industry;
            });
          this.companyIndustries.push(...[companyIndustry]);
          this.selectedIndustry = null;

          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Industry added successfully.",
          });
        },
        error: (error) => {
          if (error == "CONFLICT") {
            this.messageService.add({
              severity: "error",
              summary: "Error",
              detail: "Industry already exists.",
            });
          } else {
            this.messageService.add({
              severity: "error",
              summary: "Error",
              detail: "An error has occured",
            });
          }
        },
      });
  }

  deleteCompanyIndustry(industryId: number) {
    this.databaseService
      .deleteCompanyIndustry(this.companyId, industryId)
      .pipe(first())
      .subscribe({
        next: () => {
          this.companyIndustries = this.companyIndustries.filter(
            (companyIndustry) => companyIndustry.industryId != industryId
          );
          this.messageService.add({
            severity: "success",
            summary: "Success",
            detail: "Industry deleted successfully.",
          });
        },
        error: (error) => {
          this.messageService.add({
            severity: "error",
            summary: "Error",
            detail: "An error has occured",
          });
        },
      });
  }

  showCompanyDetailsForm() {
    this.displayCompanyDetailsForm = true;
    this.companyName = this.company.name;
    this.companyWebsite = this.company.website;
    this.companyDescription = this.company.description;
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
  }

  saveCompanyDetails() {
    if (
      !this.companyName ||
      !this.companyWebsite ||
      !this.selectedCity ||
      !this.companyDescription
    ) {
      this.messageService.add({
        severity: "error",
        summary: "Error",
        detail: "All fields are required.",
      });
    } else {
      console.log(this.selectedCity);
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
          next: () => {
            this.displayCompanyDetailsForm = false;

            this.databaseService
              .getCompanyById(this.companyId)
              .subscribe((company) => {
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
                company.photo = company.photo + `?${Date.now()}`;
                this.company = company;
              });
            this.messageService.add({
              severity: "success",
              summary: "Success",
              detail: "Details updated successfully.",
            });
          },
          error: (error) => {
            console.log("error");
            this.messageService.add({
              severity: "error",
              summary: "Error",
              detail: "An error has occured",
            });
          },
        });
    }

    if (this.imageUpload.hasFiles()) {
      this.databaseService
        .uploadCompanyImage(this.company.id, this.imageUpload)
        .pipe(first())
        .subscribe({
          next: (event: any) => {
            this.messageService.add({
              severity: "success",
              summary: "Success",
              detail: "Picture updated successfully.",
            });
          },
          error: (error) => {
            this.messageService.add({
              severity: "error",
              summary: "Error",
              detail: "Only images are allowed.",
            });
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
