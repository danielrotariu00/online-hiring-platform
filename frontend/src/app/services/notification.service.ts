import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";

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

  deleteNotification(notificationId: number): Observable<Notification> {
    return this.http.delete(
      `${environment.notificationApiURL}/notifications/${notificationId}`
    ) as Observable<Notification>;
  }
}
