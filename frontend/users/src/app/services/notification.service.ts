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
      `${environment.notificationsApiURL}/users/${userId}/notifications`
    ) as Observable<Notification[]>;
  }

  markNotificationAsRead(userId: number, notificationId: number) {
    return this.http.put(
      `${environment.notificationsApiURL}/users/${userId}/notifications/${notificationId}`,
      true
    );
  }

  deleteNotification(
    userId: number,
    notificationId: number
  ): Observable<Notification> {
    return this.http.delete(
      `${environment.notificationsApiURL}/users/${userId}/notifications/${notificationId}`
    ) as Observable<Notification>;
  }
}
