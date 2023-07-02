import { Component, Inject, LOCALE_ID, OnInit, ViewChild } from "@angular/core";
import { formatDate } from "@angular/common";
import { ActivatedRoute } from "@angular/router";
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
  City,
  JobStatus,
} from "../../models";

import { DatabaseService } from "../../services";

@Component({
  selector: "app-jobs",
  templateUrl: "./jobs.component.html",
  styleUrls: ["./jobs.component.scss"],
})
export class JobsComponent implements OnInit {
  @ViewChild("paginator", { static: true }) paginator: Paginator;

  isLoading: boolean = false;

  jobs: Job[];
  selectedJob: Job;

  totalElements: number = 0;
  totalPages: number = 0;
  maxJobs: number = 5;

  title: String;

  countries: Country[];
  selectedCountries: Country[] = [];

  cities: City[];
  selectedCities: City[] = [];

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
  openjobStatusId: number;

  description: String;

  postedSince: Date;
  currentDate: Date;

  queryParams: any;

  constructor(
    private route: ActivatedRoute,
    private databaseService: DatabaseService,
    @Inject(LOCALE_ID) private locale: string
  ) {}

  ngOnInit(): void {
    this.currentDate = new Date();

    this.route.queryParams.subscribe((params) => {
      this.queryParams = params;
      console.log(this.queryParams);
    });

    this.databaseService
      .getJobStatus()
      .subscribe((jobStatusList: JobStatus[]) => {
        this.openjobStatusId = jobStatusList
          .filter((jobStatus) => jobStatus.name === "OPEN")
          .map((jobStatus) => jobStatus.id)[0];

        this.isLoading = true;
        this.databaseService
          .getJobs(
            0,
            this.maxJobs,
            true,
            this.openjobStatusId,
            undefined,
            this.selectedCountries,
            this.selectedCities,
            this.selectedCompanies,
            this.selectedIndustries
          )
          .subscribe((jobQueryResponse: JobQueryResponse) => {
            this.jobs = this.toJobList(jobQueryResponse.jobList);
            this.jobs = this.jobs.map((job) => {
              job.formattedTimestamp = formatDate(
                job.postedAt,
                "d MMM y, h:mm:ss a",
                this.locale
              );

              return job;
            });
            this.totalElements = jobQueryResponse.totalElements;
            this.totalPages = jobQueryResponse.totalPages;
            this.selectedJob = this.jobs[0];
            setTimeout(() => this.paginator.changePage(0));
            this.isLoading = false;
          });

        this.databaseService
          .getCountries()
          .subscribe((countries: Country[]) => {
            this.countries = countries;
          });

        this.databaseService.getCities().subscribe((cities: City[]) => {
          this.cities = cities;
        });

        this.databaseService
          .getCompanies()
          .subscribe((companies: Company[]) => {
            this.companies = companies;
            this.selectedCompanies = this.companies.filter(
              (company) => company.id == this.queryParams.companyId
            );
            if (this.queryParams.companyId) {
              this.onClickFilter();
            }
          });

        this.databaseService
          .getIndustries()
          .subscribe((industries: Industry[]) => {
            this.industries = industries;
            this.selectedIndustries = this.industries.filter(
              (industry) => industry.id == this.queryParams.industryId
            );
            if (this.queryParams.industryId) {
              this.onClickFilter();
            }
          });

        this.databaseService
          .getWorkTypes()
          .subscribe((workTypes: WorkType[]) => {
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
      });
  }

  setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  onClickFilter() {
    this.jobs = [];
    this.isLoading = true;
    this.databaseService
      .getJobs(
        0,
        this.maxJobs,
        true,
        this.openjobStatusId,
        this.title,
        this.selectedCountries,
        this.selectedCities,
        this.selectedCompanies,
        this.selectedIndustries,
        this.selectedWorkTypes,
        this.selectedJobTypes,
        this.selectedExperienceLevels,
        this.description,
        this.postedSince
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.jobs = this.jobs.map((job) => {
          job.formattedTimestamp = formatDate(
            job.postedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          return job;
        });
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
        this.isLoading = false;
      });
  }

  onPageChange(event: any) {
    this.jobs = [];
    this.isLoading = true;
    this.databaseService
      .getJobs(
        event.page,
        this.maxJobs,
        true,
        this.openjobStatusId,
        this.title,
        this.selectedCountries,
        this.selectedCities,
        this.selectedCompanies,
        this.selectedIndustries,
        this.selectedWorkTypes,
        this.selectedJobTypes,
        this.selectedExperienceLevels,
        this.description,
        this.postedSince
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.jobs = this.jobs.map((job) => {
          job.formattedTimestamp = formatDate(
            job.postedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          return job;
        });
        this.isLoading = false;
      });
  }

  onClickClearFilters() {
    this.title = "";
    this.selectedCompanies = [];
    this.selectedCountries = [];
    this.selectedCities = [];
    this.selectedIndustries = [];
    this.selectedWorkTypes = [];
    this.selectedJobTypes = [];
    this.selectedExperienceLevels = [];
    this.description = "";
    this.postedSince = undefined;

    this.isLoading = true;
    this.databaseService
      .getJobs(0, this.maxJobs, true, this.openjobStatusId)
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.jobs = this.jobs.map((job) => {
          job.formattedTimestamp = formatDate(
            job.postedAt,
            "d MMM y, h:mm:ss a",
            this.locale
          );

          return job;
        });
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
        setTimeout(() => this.paginator.changePage(0));
        this.isLoading = false;
      });

    this.databaseService.getCities().subscribe((cities: City[]) => {
      this.cities = cities;
    });
  }

  onChangeCountry(event: any) {
    this.cities = [];

    if (this.selectedCountries.length != 0) {
      this.selectedCountries.map((country) => {
        this.databaseService
          .getCitiesByCountryId(country.id)
          .subscribe((cities: City[]) => {
            this.cities.push(...cities);
          });
      });
    } else {
      this.databaseService.getCities().subscribe((cities: City[]) => {
        this.cities = cities;
      });
    }
  }

  toJobList(jobResponseList: JobResponse[]) {
    return jobResponseList.map((response) =>
      this.databaseService.toJob(response)
    );
  }
}
