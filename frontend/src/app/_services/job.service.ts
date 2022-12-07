import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { City, Company, JobResponse, Country, WorkType } from "../_models";
import { Job } from "../_models/job";
import { CountryService } from "./country.service";
import { CompanyService } from "./company.service";
import { CityService } from "./city.service";
import { WorkTypeService } from "./work_type.service";

@Injectable({
  providedIn: "root",
})
export class JobService {
  constructor(
    private http: HttpClient,
    private countryService: CountryService,
    private cityService: CityService,
    private companyService: CompanyService,
    private workTypeService: WorkTypeService
  ) {}

  getJobs(
    page: number,
    maxJobs: number,
    countries?: Country[],
    companies?: Company[]
  ): Observable<JobResponse[]> {
    let URL = `${environment.searchApiURL}/jobs?page=${page}&maxJobs=${maxJobs}`;

    if (typeof countries !== "undefined") {
      const countryQueryParam = `&countryId=${countries
        .map((country) => country.id)
        .join(",")}`;

      URL += countryQueryParam;
    }

    if (typeof companies !== "undefined") {
      const companyQueryParam = `&companyId=${companies
        .map((company) => company.id)
        .join(",")}`;

      URL += companyQueryParam;
    }

    return this.http.get(URL) as Observable<JobResponse[]>;
  }

  toJob(jobResponse: JobResponse): Job {
    const job = new Job();

    job.id = jobResponse.id;
    job.title = jobResponse.title;

    this.companyService
      .getCompanyById(jobResponse.companyId)
      .subscribe((company: Company) => {
        job.company = company;
      });

    this.cityService.getCityById(jobResponse.cityId).subscribe((city: City) => {
      job.city = city;
    });

    this.countryService
      .getCountryByID(jobResponse.countryId)
      .subscribe((country: Country) => {
        job.country = country;
      });

    this.workTypeService
      .getWorkTypeById(jobResponse.workTypeId)
      .subscribe((workType: WorkType) => {
        job.workType = workType;
      });

    job.description = jobResponse.description;
    return job;
  }
}
