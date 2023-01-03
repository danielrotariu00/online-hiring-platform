import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { NewsfeedEntry } from "../models";

@Injectable({
  providedIn: "root",
})
export class NewsfeedService {
  constructor(private http: HttpClient) {}

  getNewsfeed(userId: number, maxEntries: number): Observable<NewsfeedEntry[]> {
    return this.http.get(
      `${environment.newsfeedApiURL}/users/${userId}/newsfeed?maxEntries=${maxEntries}`
    ) as Observable<NewsfeedEntry[]>;
  }
}
