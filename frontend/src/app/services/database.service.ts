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
  JobStatus,
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
import { FileUpload } from "primeng/fileupload";

@Injectable({
  providedIn: "root",
})
export class DatabaseService {
  constructor(private http: HttpClient) {}

  saveUserDetails(
    userId: number,
    firstName: string,
    lastName: string,
    phoneNumber: string,
    cityId: number,
    address: string,
    profileDescription: string,
    profilePictureUrl: string
  ) {
    return this.http.put(
      `${environment.databaseApiURL}/users/${userId}/details`,
      {
        userId,
        firstName,
        lastName,
        phoneNumber,
        cityId,
        address,
        profileDescription,
        profilePictureUrl,
      }
    );
  }

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

  getEducationalInstitutions(): Observable<EducationalInstitution[]> {
    return this.http.get(
      `${environment.databaseApiURL}/educational-institutions`
    ) as Observable<EducationalInstitution[]>;
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

  uploadUserImage(userId: number, imageUpload: FileUpload): Observable<any> {
    imageUpload.url = `${environment.databaseApiURL}/users/${userId}/details/image`;
    imageUpload.upload();
    return imageUpload.onUpload.asObservable();
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

  addUserEducationalExperience(
    userId: number,
    educationalInstitutionId: number,
    speciality: string,
    title: string,
    startDate: Date,
    endDate: Date,
    description: string
  ) {
    return this.http.post(
      `${environment.databaseApiURL}/educational-experience`,
      {
        userId,
        educationalInstitutionId,
        speciality,
        title,
        startDate,
        endDate,
        description,
      }
    );
  }

  addUserSkill(userId: number, skillId: number) {
    return this.http.post(
      `${environment.databaseApiURL}/users/${userId}/skills`,
      {
        skillId,
      }
    );
  }

  addUserProject(
    userId: number,
    name: string,
    startDate: Date,
    endDate: Date,
    description: string
  ) {
    return this.http.post(`${environment.databaseApiURL}/projects`, {
      userId,
      name,
      startDate,
      endDate,
      description,
    });
  }

  addUserLanguage(userId: number, languageId: number, languageLevelId: number) {
    return this.http.post(
      `${environment.databaseApiURL}/users/${userId}/languages`,
      {
        languageId,
        languageLevelId,
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

  editJob(
    jobId: number,
    title: string,
    recruiterId: number,
    companyIndustryId: number,
    cityId: number,
    workTypeId: number,
    jobTypeId: number,
    experienceLevelId: number,
    description: string,
    jobStatusId: number
  ) {
    return this.http.put(`${environment.databaseApiURL}/jobs/${jobId}`, {
      title,
      recruiterId,
      companyIndustryId,
      cityId,
      workTypeId,
      jobTypeId,
      experienceLevelId,
      description,
      jobStatusId,
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

  addCompanyIndustry(companyId: number, industryId: number) {
    return this.http.post(`${environment.databaseApiURL}/company-industries`, {
      companyId,
      industryId,
    });
  }

  getCompanyIndustriesByCompanyId(
    companyId: number
  ): Observable<CompanyIndustry[]> {
    return this.http.get(
      `${environment.databaseApiURL}/companies/${companyId}/company-industries`
    ) as Observable<CompanyIndustry[]>;
  }

  deleteCompanyIndustry(
    companyId: number,
    industryId: number
  ): Observable<CompanyIndustry> {
    return this.http.delete(
      `${environment.databaseApiURL}/companies/${companyId}/industries/${industryId}`
    ) as Observable<CompanyIndustry>;
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

  getJobStatus(): Observable<JobStatus[]> {
    return this.http.get(
      `${environment.databaseApiURL}/job-status`
    ) as Observable<JobStatus[]>;
  }

  getJobStatusById(id: number): Observable<JobStatus> {
    return this.http.get(
      `${environment.databaseApiURL}/job-status/${id}`
    ) as Observable<JobStatus>;
  }

  padTo2Digits(num: number) {
    return num.toString().padStart(2, "0");
  }

  getJobs(
    page: number,
    maxJobs: number,
    open: boolean,
    title?: String,
    countries?: Country[],
    cities?: City[],
    companies?: Company[],
    industries?: Industry[],
    workTypes?: WorkType[],
    jobTypes?: JobType[],
    experienceLevels?: ExperienceLevel[],
    description?: String,
    postedSince?: Date,
    cached?: boolean
  ): Observable<JobQueryResponse> {
    let URL = `${environment.searchApiURL}/jobs?page=${page}&size=${maxJobs}`;

    if (typeof title !== "undefined") {
      const titleQueryParam = `&title=${title}`;

      URL += titleQueryParam;
    }

    if (typeof countries !== "undefined") {
      const countryQueryParam = `&countryId=${countries
        .map((country) => country.id)
        .join(",")}`;

      URL += countryQueryParam;
    }

    if (typeof cities !== "undefined") {
      const cityQueryParam = `&cityId=${cities
        .map((city) => city.id)
        .join(",")}`;

      URL += cityQueryParam;
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

    if (typeof description !== "undefined") {
      const descriptionQueryParam = `&descriptionKeyword=${description
        .split(" ")
        .join(",")}`;

      URL += descriptionQueryParam;
    }

    if (typeof postedSince !== "undefined") {
      console.log("3");
      const postedSinceQueryParam = `&postedSince=${
        [
          postedSince.getFullYear(),
          this.padTo2Digits(postedSince.getMonth() + 1),
          this.padTo2Digits(postedSince.getDate()),
        ].join("-") + " 00:00:00"
      }`;

      console.log(postedSinceQueryParam);
      URL += postedSinceQueryParam;
    }

    if (typeof cached !== "undefined") {
      const cachedQueryParam = `&cached=${cached}`;

      URL += cachedQueryParam;
    }

    if (open) {
      const jobStatusQueryParam = `&jobStatusId=1`;

      URL += jobStatusQueryParam;
    }

    return this.http.get(URL) as Observable<JobQueryResponse>;
  }

  getJobById(id: number): Observable<JobResponse> {
    return this.http.get(
      `${environment.databaseApiURL}/jobs/${id}`
    ) as Observable<JobResponse>;
  }

  getCompanyIndustriesFollowedByUser(
    userId: number
  ): Observable<CompanyIndustry[]> {
    return this.http.get(
      `${environment.databaseApiURL}/users/${userId}/followed-company-industries`
    ) as Observable<CompanyIndustry[]>;
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

  deleteCompanyIndustryFollower(userId: number, companyIndustryId: number) {
    return this.http.delete(
      `${environment.databaseApiURL}/users/${userId}/followed-company-industries/${companyIndustryId}`
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

    this.getJobTypeById(jobResponse.jobTypeId).subscribe((jobType: JobType) => {
      job.jobType = jobType;
    });

    this.getExperienceLevelById(jobResponse.experienceLevelId).subscribe(
      (experienceLevel: ExperienceLevel) => {
        job.experienceLevel = experienceLevel;
      }
    );

    this.getJobStatusById(jobResponse.jobStatusId).subscribe(
      (jobStatus: JobStatus) => {
        job.jobStatus = jobStatus;
      }
    );

    this.getIndustryByID(jobResponse.industryId).subscribe(
      (industry: Industry) => {
        job.industry = industry;
      }
    );

    job.description = jobResponse.description;
    return job;
  }
}
