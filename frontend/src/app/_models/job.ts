import { City } from "./city";
import { Company } from "./company";
import { Country } from "./country";
import { WorkType } from "./work_type";

export class Job {
  id: number;
  title: string;
  recruiterId: number;
  company: Company;
  city: City;
  country: Country;
  workType: WorkType;
  jobTypeId: number;
  experienceLevelId: number;
  companyIndustryId: number;
  industryId: number;
  description: string;
  jobStatusId: number;
  postedAt: string;
}
