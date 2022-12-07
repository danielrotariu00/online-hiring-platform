import { Component, OnInit } from "@angular/core";
import { Company, Job, JobResponse } from "../_models";
import { Country } from "../_models/country";

import { CountryService, JobService, CompanyService } from "../_services";

@Component({
  selector: "app-jobs",
  templateUrl: "./jobs.component.html",
  styleUrls: ["./jobs.component.css"],
})
export class JobsComponent implements OnInit {
  jobs: Job[];
  page: number = 1;
  maxJobs: number = 10;
  selectedJob: Job;

  countries: Country[];
  selectedCountries: Country[] = [];

  companies: Company[];
  selectedCompanies: Company[] = [];

  constructor(
    private jobService: JobService,
    private countryService: CountryService,
    private companyService: CompanyService
  ) {}

  ngOnInit(): void {
    this.jobService
      .getJobs(this.page, this.maxJobs)
      .subscribe((jobResponseList: JobResponse[]) => {
        this.jobs = this.toJobList(jobResponseList);
        this.selectedJob = this.jobs[0];
      });

    this.countryService.getCountries().subscribe((countries: Country[]) => {
      this.countries = countries;
    });

    this.companyService.getCompanies().subscribe((companies: Company[]) => {
      this.companies = companies;
    });
  }

  setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  onClickLoadMore() {
    this.jobService
      .getJobs(
        ++this.page,
        this.maxJobs,
        this.selectedCountries,
        this.selectedCompanies
      )
      .subscribe((jobResponseList: JobResponse[]) => {
        this.jobs.push(...this.toJobList(jobResponseList));
      });
  }

  onClickFilter() {
    this.jobs = [];
    this.page = 1;
    this.jobService
      .getJobs(
        this.page,
        this.maxJobs,
        this.selectedCountries,
        this.selectedCompanies
      )
      .subscribe((jobResponseList: JobResponse[]) => {
        this.jobs = this.toJobList(jobResponseList);
        this.selectedJob = this.jobs[0];
      });
  }

  toJobList(jobResponseList: JobResponse[]) {
    return jobResponseList.map((response) => this.jobService.toJob(response));
  }
}
