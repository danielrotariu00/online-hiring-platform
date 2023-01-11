export interface Notification {
  id: number;
  userId: number;
  jobApplicationId: number;
  text: string;
  isRead: boolean;
  timestamp: Date;
  formattedTimestamp: string;
}
