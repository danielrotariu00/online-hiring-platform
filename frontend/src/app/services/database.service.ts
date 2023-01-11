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
  UserDetails,
  WorkType,
} from "../models";
import { UserEducationalExperience } from "../models/user_educational_experience";
import { EducationalInstitution } from "../models/educational_institution";
import { UserProfessionalExperience } from "../models/user_professional_experience";
import { UserSkill } from "../models/user_skill";
import { Skill } from "../models/skill";
import { UserLanguage } from "../models/user_language";
import { Language } from "../models/language";
import { UserProject } from "../models/user_project";
import { LanguageLevel } from "../models/language_level";

@Injectable({
  providedIn: "root",
})
export class DatabaseService {
  constructor(private http: HttpClient) {}

  getUserDetails(userId: number): Observable<UserDetails> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/details`
    ) as Observable<UserDetails>;
  }

  getUserEducationalExperience(
    userId: number
  ): Observable<UserEducationalExperience[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/educational-experience`
    ) as Observable<UserEducationalExperience[]>;
  }

  getUserProfessionalExperience(
    userId: number
  ): Observable<UserProfessionalExperience[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/professional-experience`
    ) as Observable<UserProfessionalExperience[]>;
  }

  getUserSkills(userId: number): Observable<UserSkill[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/skills`
    ) as Observable<UserSkill[]>;
  }

  getUserProjects(userId: number): Observable<UserProject[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/projects`
    ) as Observable<UserProject[]>;
  }

  getUserLanguages(userId: number): Observable<UserLanguage[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/languages`
    ) as Observable<UserLanguage[]>;
  }

  getEducationalInstitutionById(
    id: number
  ): Observable<EducationalInstitution> {
    return this.http.get(
      `${environment.databaseApiURL}/educational-institutions/${id}`
    ) as Observable<EducationalInstitution>;
  }

  getSkillById(id: number): Observable<Skill> {
    return this.http.get(
      `${environment.databaseApiURL}/skills/${id}`
    ) as Observable<Skill>;
  }

  getSkills(): Observable<Skill[]> {
    return this.http.get(`${environment.databaseApiURL}/skills`) as Observable<
      Skill[]
    >;
  }

  getLanguageById(id: number): Observable<Language> {
    return this.http.get(
      `${environment.databaseApiURL}/languages/${id}`
    ) as Observable<Language>;
  }

  getLanguages(): Observable<Language[]> {
    return this.http.get(
      `${environment.databaseApiURL}/languages`
    ) as Observable<Language[]>;
  }

  getLanguageLevels(): Observable<LanguageLevel[]> {
    return this.http.get(
      `${environment.databaseApiURL}/language-levels`
    ) as Observable<LanguageLevel[]>;
  }

  getLanguageLevelById(id: number): Observable<LanguageLevel> {
    return this.http.get(
      `${environment.databaseApiURL}/language-levels/${id}`
    ) as Observable<LanguageLevel>;
  }

  deleteUserProfessionalExperience(
    id: number
  ): Observable<UserProfessionalExperience> {
    return this.http.delete(
      `${environment.databaseApiURL}/professional-experience/${id}`
    ) as Observable<UserProfessionalExperience>;
  }

  deleteUserEducationalExperience(
    id: number
  ): Observable<UserEducationalExperience> {
    return this.http.delete(
      `${environment.databaseApiURL}/educational-experience/${id}`
    ) as Observable<UserEducationalExperience>;
  }

  deleteUserSkill(userId: number, skillId: number): Observable<UserSkill> {
    return this.http.delete(
      `${environment.databaseApiURL}/users/${userId}/skills/${skillId}`
    ) as Observable<UserSkill>;
  }

  deleteUserProject(id: number): Observable<UserProject> {
    return this.http.delete(
      `${environment.databaseApiURL}/projects/${id}`
    ) as Observable<UserProject>;
  }

  deleteUserLanguage(
    userId: number,
    languageId: number
  ): Observable<UserLanguage> {
    return this.http.delete(
      `${environment.databaseApiURL}/users/${userId}/languages/${languageId}`
    ) as Observable<UserLanguage>;
  }

  addUserProfessionalExperience(
    userId: number,
    companyId: number,
    jobTitle: string,
    startDate: Date,
    endDate: Date,
    description: string
  ) {
    return this.http.post(
      `${environment.databaseApiURL}/professional-experience`,
      {
        userId,
        companyId,
        jobTitle,
        startDate,
        endDate,
        description,
      }
    );
  }

  addJob(
    title: string,
    recruiterId: number,
    companyIndustryId: number,
    cityId: number,
    workTypeId: number,
    jobTypeId: number,
    experienceLevelId: number,
    description: string
  ) {
    return this.http.post(`${environment.databaseApiURL}/jobs`, {
      title,
      recruiterId,
      companyIndustryId,
      cityId,
      workTypeId,
      jobTypeId,
      experienceLevelId,
      description,
    });
  }

  getCities(): Observable<City[]> {
    return this.http.get(`${environment.databaseApiURL}/cities`) as Observable<
      City[]
    >;
  }

  getCitiesByCountryId(countryId: number): Observable<City[]> {
    return this.http.get(
      `${environment.databaseApiURL}/countries/${countryId}/cities`
    ) as Observable<City[]>;
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
    experienceLevels?: ExperienceLevel[],
    cached?: boolean
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
        .map((industry) => industry?.id)
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

    if (typeof cached !== "undefined") {
      const cachedQueryParam = `&cached=${cached}`;

      URL += cachedQueryParam;
    }

    return this.http.get(URL) as Observable<JobQueryResponse>;
  }

  getJobById(id: number): Observable<JobResponse> {
    return this.http.get(
      `${environment.databaseApiURL}/jobs/${id}`
    ) as Observable<JobResponse>;
  }

  createCompanyIndustryFollower(userId: number, companyIndustryId: number) {
    return this.http.post(
      `${environment.databaseApiURL}/company-industry-followers`,
      {
        userId,
        companyIndustryId,
      }
    );
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
