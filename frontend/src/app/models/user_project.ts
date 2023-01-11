export interface UserProject {
  id: number;
  name: string;
  startDate: Date;
  endDate: Date;
  description: string;
  formattedStartDate?: string;
  formattedEndDate?: string;
}
