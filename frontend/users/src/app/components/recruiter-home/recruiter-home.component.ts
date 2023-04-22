import { Component, OnInit, ViewChild } from "@angular/core";
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
  User,
  WorkType,
} from "../../models";
import { AccountService, DatabaseService } from "../../services";
import { Router } from "@angular/router";
import { Paginator } from "primeng/paginator";
import { first } from "rxjs";

@Component({
  selector: "app-companies",
  templateUrl: "./recruiter-home.component.html",
  styleUrls: ["./recruiter-home.component.scss"],
})
export class RecruiterHomeComponent implements OnInit {
  @ViewChild("paginator", { static: true }) paginator: Paginator;
  loggedUser?: User | null;

  companyIndustries: CompanyIndustry[];
  selectedCompanyIndustry: CompanyIndustry;
  jobs: Job[] = [];
  selectedJob: Job;
  totalElements: number = 0;
  totalPages: number = 0;

  maxJobs: number = 4;

  companyId: number;
  company: Company;

  displayJobForm: boolean = false;
  edit: boolean = false;
  jobTitle: string;
  countries: Country[];
  selectedCountry: Country;
  cities: City[];
  selectedCity: City;
  selectedFormCompanyIndustry: CompanyIndustry;
  jobTypes: JobType[];
  selectedJobType: JobType;
  workTypes: WorkType[];
  selectedWorkType: WorkType;
  experienceLevels: ExperienceLevel[];
  selectedExperienceLevel: ExperienceLevel;
  jobStatusList: JobStatus[];
  selectedJobStatus: JobStatus;
  description: string;

  constructor(
    private router: Router,
    private accountService: AccountService,
    private databaseService: DatabaseService
  ) {}

  ngOnInit(): void {
    this.accountService.user.subscribe((x) => {
      this.loggedUser = x;
      this.companyId = this.loggedUser.companyId;

      this.databaseService
        .getCompanyById(this.companyId)
        .subscribe((company: Company) => {
          this.company = company;

          this.databaseService
            .getCompanyIndustriesByCompanyId(this.companyId)
            .subscribe((companyIndustries: CompanyIndustry[]) => {
              this.companyIndustries = companyIndustries.map(
                (companyIndustry) => {
                  this.databaseService
                    .getIndustryByID(companyIndustry.industryId)
                    .subscribe((industry: Industry) => {
                      companyIndustry.industry = industry;
                    });

                  return companyIndustry;
                }
              );

              this.databaseService
                .getJobs(
                  0,
                  this.maxJobs,
                  false,
                  undefined,
                  undefined,
                  undefined,
                  [this.company],
                  undefined,
                  undefined,
                  undefined,
                  undefined,
                  undefined,
                  undefined,
                  false
                )
                .subscribe((jobQueryResponse: JobQueryResponse) => {
                  this.jobs = this.toJobList(jobQueryResponse.jobList);
                  this.totalElements = jobQueryResponse.totalElements;
                  this.totalPages = jobQueryResponse.totalPages;
                  this.selectedJob = this.jobs[0];
                  setTimeout(() => this.paginator.changePage(0));
                });
            });
        });
    });
  }

  onIndustrySelectionChange(event) {
    let industryId = event.value[0]?.industryId;
    this.selectedCompanyIndustry = this.companyIndustries.find(
      (companyIndustry) => companyIndustry?.industryId == industryId
    );
    this.databaseService
      .getJobs(
        0,
        this.maxJobs,
        false,
        undefined,
        undefined,
        undefined,
        [this.company],
        [this.selectedCompanyIndustry?.industry],
        undefined,
        undefined,
        undefined,
        undefined,
        undefined,
        false
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
        this.totalElements = jobQueryResponse.totalElements;
        this.totalPages = jobQueryResponse.totalPages;
        this.selectedJob = this.jobs[0];
        setTimeout(() => this.paginator.changePage(0));
      });
  }

  onPageChange(event: any) {
    this.jobs = [];
    this.databaseService
      .getJobs(
        event.page,
        this.maxJobs,
        false,
        undefined,
        undefined,
        undefined,
        [this.company],
        [this.selectedCompanyIndustry?.industry],
        undefined,
        undefined,
        undefined,
        undefined,
        undefined,
        false
      )
      .subscribe((jobQueryResponse: JobQueryResponse) => {
        this.jobs = this.toJobList(jobQueryResponse.jobList);
      });
  }

  setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  editJob(job) {}

  viewApplications(job) {
    this.router.navigate(["/recruiter-job-applications"], {
      queryParams: {
        jobId: `${job.id}`,
      },
    });
  }

  showJobForm(job: Job) {
    this.displayJobForm = true;
    this.edit = false;

    this.databaseService.getCountries().subscribe((countries: Country[]) => {
      this.countries = countries;
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

    this.databaseService
      .getJobStatus()
      .subscribe((jobStatusList: JobStatus[]) => {
        this.jobStatusList = jobStatusList;
      });

    if (job !== undefined) {
      this.edit = true;
      this.setSelectedJob(job);
      this.jobTitle = job.title;
      for (var companyIndustry of this.companyIndustries) {
        if (companyIndustry.industryId == job.industry.id) {
          this.selectedFormCompanyIndustry = companyIndustry;
          break;
        }
      }
      this.selectedCountry = job.country;
      this.onCountryChange(undefined);
      this.selectedCity = job.city;
      this.selectedJobType = job.jobType;
      this.selectedWorkType = job.workType;
      this.selectedExperienceLevel = job.experienceLevel;
      this.selectedJobStatus = job.jobStatus;
      this.description = job.description;
    }
  }

  saveJob() {
    if (this.edit) {
      this.databaseService
        .editJob(
          this.selectedJob.id,
          this.jobTitle,
          this.loggedUser.id,
          this.selectedFormCompanyIndustry.id,
          this.selectedCity.id,
          this.selectedWorkType.id,
          this.selectedJobType.id,
          this.selectedExperienceLevel.id,
          this.description,
          this.selectedJobStatus.id
        )
        .pipe(first())
        .subscribe({
          next: () => {
            this.displayJobForm = false;
            this.databaseService
              .getJobs(
                0,
                this.maxJobs,
                false,
                undefined,
                undefined,
                undefined,
                [this.company],
                undefined,
                undefined,
                undefined,
                undefined,
                undefined,
                undefined,
                false
              )
              .subscribe((jobQueryResponse: JobQueryResponse) => {
                this.jobs = this.toJobList(jobQueryResponse.jobList);
                this.totalElements = jobQueryResponse.totalElements;
                this.totalPages = jobQueryResponse.totalPages;
                this.selectedJob = this.jobs[0];
                setTimeout(() => this.paginator.changePage(0));
              });
          },
          error: (error) => {
            console.log("error");
          },
        });
    } else {
      this.databaseService
        .addJob(
          this.jobTitle,
          this.loggedUser.id,
          this.selectedFormCompanyIndustry.id,
          this.selectedCity.id,
          this.selectedWorkType.id,
          this.selectedJobType.id,
          this.selectedExperienceLevel.id,
          this.description
        )
        .pipe(first())
        .subscribe({
          next: () => {
            this.displayJobForm = false;
            this.databaseService
              .getJobs(
                0,
                this.maxJobs,
                false,
                undefined,
                undefined,
                undefined,
                [this.company],
                undefined,
                undefined,
                undefined,
                undefined,
                undefined,
                undefined,
                false
              )
              .subscribe((jobQueryResponse: JobQueryResponse) => {
                this.jobs = this.toJobList(jobQueryResponse.jobList);
                this.totalElements = jobQueryResponse.totalElements;
                this.totalPages = jobQueryResponse.totalPages;
                this.selectedJob = this.jobs[0];
                setTimeout(() => this.paginator.changePage(0));
              });
          },
          error: (error) => {
            console.log("error");
          },
        });
    }
  }

  onCountryChange(event) {
    this.databaseService
      .getCitiesByCountryId(this.selectedCountry.id)
      .subscribe((cities: City[]) => {
        this.cities = cities;
      });
  }

  toJobList(jobResponseList: JobResponse[]) {
    return jobResponseList.map((response) =>
      this.databaseService.toJob(response)
    );
  }
}
