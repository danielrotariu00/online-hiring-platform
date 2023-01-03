import { Component, OnInit, ViewChild } from "@angular/core";
import { Paginator } from "primeng/paginator";
import {
  Country,
  Company,
  ExperienceLevel,
  Industry,
  Job,
  JobQueryResponse,
  JobResponse,
  JobType,
  WorkType,
} from "../models";

import { DatabaseService } from "../services";

@Component({
  selector: "app-jobs",
  templateUrl: "./jobs.component.html",
  styleUrls: ["./jobs.component.scss"],
})
export class JobsComponent implements OnInit {
  @ViewChild("paginator", { static: true }) paginator: Paginator;

  jobs: Job[];
  selectedJob: Job;
  totalElements: number = 0;
  totalPages: number = 0;

  maxJobs: number = 5;

  countries: Country[];
  selectedCountries: Country[] = [];

  companies: Company[];
  selectedCompanies: Company[] = [];

  industries: Industry[];
  selectedIndustries: Industry[] = [];

  workTypes: WorkType[];
  selectedWorkTypes: WorkType[] = [];

  jobTypes: JobType[];
  selectedJobTypes: JobType[] = [];

  experienceLevels: ExperienceLevel[];
  selectedExperienceLevels: ExperienceLevel[] = [];

  constructor(private databaseService: DatabaseService) {}

  ngOnInit(): void {
    this.databaseService
      .getJobs(0, this.maxJobs)
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
        setTimeout(() => this.paginator.changePage(0));
      });

    this.databaseService.getCountries().subscribe((countries: Country[]) => {
      this.countries = countries;
    });

    this.databaseService.getCompanies().subscribe((companies: Company[]) => {
      this.companies = companies;
    });

    this.databaseService.getIndustries().subscribe((industries: Industry[]) => {
      this.industries = industries;
    });

    this.databaseService.getWorkTypes().subscribe((workTypes: WorkType[]) => {
      this.workTypes = workTypes;
    });

    this.databaseService.getJobTypes().subscribe((jobTypes: JobType[]) => {
      this.jobTypes = jobTypes;
    });

    this.databaseService
      .getExperienceLevels()
      .subscribe((experienceLevels: ExperienceLevel[]) => {
        this.experienceLevels = experienceLevels;
      });
  }

  setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  onClickFilter() {
    this.jobs = [];
    this.databaseService
      .getJobs(
        0,
        this.maxJobs,
        this.selectedCountries,
        this.selectedCompanies,
        this.selectedIndustries,
        this.selectedWorkTypes,
        this.selectedJobTypes,
        this.selectedExperienceLevels
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
      });
  }

  onPageChange(event: any) {
    this.jobs = [];
    this.databaseService
      .getJobs(
        event.page,
        this.maxJobs,
        this.selectedCountries,
        this.selectedCompanies,
        this.selectedIndustries,
        this.selectedWorkTypes,
        this.selectedJobTypes,
        this.selectedExperienceLevels
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
      });
  }

  onClickClearFilters() {
    this.selectedCompanies = [];
    this.selectedCountries = [];
    this.selectedIndustries = [];
    this.selectedWorkTypes = [];
    this.selectedJobTypes = [];
    this.selectedExperienceLevels = [];

    this.databaseService
      .getJobs(0, this.maxJobs)
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
        setTimeout(() => this.paginator.changePage(0));
      });
  }

  toJobList(jobResponseList: JobResponse[]) {
    return jobResponseList.map((response) =>
      this.databaseService.toJob(response)
    );
  }
}
