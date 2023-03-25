import { Industry } from "./industry";

export interface CompanyIndustry {
  id: number;
  companyId: number;
  industryId: number;
  industry?: Industry;
  isFollowed?: boolean;
}
