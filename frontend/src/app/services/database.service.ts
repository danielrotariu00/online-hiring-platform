import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import {
  City,
  Company,
  CompanyIndustry,
  Country,
  ExperienceLevel,
  Industry,
  Job,
  JobQueryResponse,
  JobResponse,
  JobType,
  WorkType,
} from "../models";

@Injectable({
  providedIn: "root",
})
export class DatabaseService {
  constructor(private http: HttpClient) {}

  getCities(): Observable<City[]> {
    return this.http.get(`${environment.databaseApiURL}/cities`) as Observable<
      City[]
    >;
  }

  getCityById(id: number): Observable<City> {
    return this.http.get(
      `${environment.databaseApiURL}/cities/${id}`
    ) as Observable<City>;
  }

  getCompanies(): Observable<Company[]> {
    return this.http.get(
      `${environment.databaseApiURL}/companies`
    ) as Observable<Company[]>;
  }

  getCompanyById(id: number): Observable<Company> {
    return this.http.get(
      `${environment.databaseApiURL}/companies/${id}`
    ) as Observable<Company>;
  }

  getCompaniesByIndustryId(industryId: number): Observable<Company[]> {
    return this.http.get(
      `${environment.databaseApiURL}/industries/${industryId}/companies`
    ) as Observable<Company[]>;
  }

  getCompanyIndustriesByCompanyId(
    companyId: number
  ): Observable<CompanyIndustry[]> {
    return this.http.get(
      `${environment.databaseApiURL}/companies/${companyId}/company-industries`
    ) as Observable<CompanyIndustry[]>;
  }

  getCountries(): Observable<Country[]> {
    return this.http.get(
      `${environment.databaseApiURL}/countries`
    ) as Observable<Country[]>;
  }

  getCountryById(id: number): Observable<Country> {
    return this.http.get(
      `${environment.databaseApiURL}/countries/${id}`
    ) as Observable<Country>;
  }

  getExperienceLevels(): Observable<ExperienceLevel[]> {
    return this.http.get(
      `${environment.databaseApiURL}/experience-levels`
    ) as Observable<ExperienceLevel[]>;
  }

  getExperienceLevelById(id: number): Observable<ExperienceLevel> {
    return this.http.get(
      `${environment.databaseApiURL}/experience-levels/${id}`
    ) as Observable<ExperienceLevel>;
  }

  getIndustries(): Observable<Industry[]> {
    return this.http.get(
      `${environment.databaseApiURL}/industries`
    ) as Observable<Industry[]>;
  }

  getIndustryByID(id: number): Observable<Industry> {
    return this.http.get(
      `${environment.databaseApiURL}/industries/${id}`
    ) as Observable<Industry>;
  }

  getJobTypes(): Observable<JobType[]> {
    return this.http.get(
      `${environment.databaseApiURL}/job-types`
    ) as Observable<JobType[]>;
  }

  getJobTypeById(id: number): Observable<JobType> {
    return this.http.get(
      `${environment.databaseApiURL}/job-types/${id}`
    ) as Observable<JobType>;
  }

  getWorkTypes(): Observable<WorkType[]> {
    return this.http.get(
      `${environment.databaseApiURL}/work-types`
    ) as Observable<WorkType[]>;
  }

  getWorkTypeById(id: number): Observable<WorkType> {
    return this.http.get(
      `${environment.databaseApiURL}/work-types/${id}`
    ) as Observable<WorkType>;
  }

  getJobs(
    page: number,
    maxJobs: number,
    countries?: Country[],
    companies?: Company[],
    industries?: Industry[],
    workTypes?: WorkType[],
    jobTypes?: JobType[],
    experienceLevels?: ExperienceLevel[]
  ): Observable<JobQueryResponse> {
    let URL = `${environment.searchApiURL}/jobs?page=${page}&size=${maxJobs}`;

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

    if (typeof industries !== "undefined") {
      const industryQueryParam = `&industryId=${industries
        .map((industry) => industry.id)
        .join(",")}`;

      URL += industryQueryParam;
    }

    if (typeof workTypes !== "undefined") {
      const workTypeQueryParam = `&workTypeId=${workTypes
        .map((workType) => workType.id)
        .join(",")}`;

      URL += workTypeQueryParam;
    }

    if (typeof jobTypes !== "undefined") {
      const jobTypeQueryParam = `&jobTypeId=${jobTypes
        .map((jobType) => jobType.id)
        .join(",")}`;

      URL += jobTypeQueryParam;
    }

    if (typeof experienceLevels !== "undefined") {
      const experienceLevelQueryParam = `&experienceLevelId=${experienceLevels
        .map((experienceLevel) => experienceLevel.id)
        .join(",")}`;

      URL += experienceLevelQueryParam;
    }

    return this.http.get(URL) as Observable<JobQueryResponse>;
  }

  getJobById(id: number): Observable<JobResponse> {
    return this.http.get(
      `${environment.databaseApiURL}/jobs/${id}`
    ) as Observable<JobResponse>;
  }

  toJob(jobResponse: JobResponse): Job {
    const job = new Job();

    job.id = jobResponse.id;
    job.title = jobResponse.title;

    this.getCompanyById(jobResponse.companyId).subscribe((company: Company) => {
      job.company = company;
    });

    this.getCityById(jobResponse.cityId).subscribe((city: City) => {
      job.city = city;
    });

    this.getCountryById(jobResponse.countryId).subscribe((country: Country) => {
      job.country = country;
    });

    this.getWorkTypeById(jobResponse.workTypeId).subscribe(
      (workType: WorkType) => {
        job.workType = workType;
      }
    );

    job.description = jobResponse.description;
    return job;
  }
}
