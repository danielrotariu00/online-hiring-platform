import { City } from "./city";
import { Company } from "./company";
import { CompanyIndustry } from "./company_industry";
import { Country } from "./country";
import { ExperienceLevel } from "./experience_level";
import { Industry } from "./industry";
import { JobStatus } from "./job_status";
import { JobType } from "./job_type";
import { WorkType } from "./work_type";

export class Job {
  id: number;
  title: string;
  recruiterId: number;
  company: Company;
  city: City;
  country: Country;
  workType: WorkType;
  jobType: JobType;
  experienceLevel: ExperienceLevel;
  jobStatus: JobStatus;
  companyIndustryId: number;
  industry: Industry;
  description: string;
  jobStatusId: number;
  postedAt: string;
}
