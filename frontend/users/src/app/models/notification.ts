import { JobApplicationMessage } from "./job_application_message";

export interface Notification {
  id: number;
  userId: number;
  jobApplicationId: string;
  text: string;
  isRead: boolean;
  message: JobApplicationMessage;
  timestamp: Date;
  formattedTimestamp: string;
}
