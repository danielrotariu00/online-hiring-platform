export interface JobApplicationMessage {
  userId: number;
  content: string;
  timestamp?: Date;
  formattedTimestamp?: string;
}
