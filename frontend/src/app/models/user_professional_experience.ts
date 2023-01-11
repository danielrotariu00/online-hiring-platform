import { Company } from "./company";

export interface UserProfessionalExperience {
  id: number;
  companyId: number;
  jobTitle: string;
  startDate: Date;
  endDate: Date;
  description: string;
  company?: Company;
  formattedStartDate?: string;
  formattedEndDate?: string;
}
