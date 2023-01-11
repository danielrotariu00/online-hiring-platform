import { EducationalInstitution } from "./educational_institution";

export interface UserEducationalExperience {
  id: number;
  educationalInstitutionId: number;
  speciality: string;
  title: string;
  startDate: Date;
  endDate: Date;
  description: string;
  educationalInstitution?: EducationalInstitution;
  formattedStartDate?: string;
  formattedEndDate?: string;
}
