import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { Notification } from "../models";

@Injectable({ providedIn: "root" })
export class NotificationService {
  constructor(private http: HttpClient) {}

  getUserNotifications(userId: number): Observable<Notification[]> {
    return this.http.get(
      `${environment.notificationApiURL}/users/${userId}/notifications`
    ) as Observable<Notification[]>;
  }

  markNotificationAsRead(notificationId: number) {
    return this.http.put(
      `${environment.notificationApiURL}/notifications/${notificationId}`,
      true
    );
  }

  deleteNotification(notificationId: number): Observable<Notification> {
    return this.http.delete(
      `${environment.notificationApiURL}/notifications/${notificationId}`
    ) as Observable<Notification>;
  }
}
