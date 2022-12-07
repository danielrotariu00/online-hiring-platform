import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { WorkType } from "../_models";

@Injectable({
  providedIn: "root",
})
export class WorkTypeService {
  constructor(private http: HttpClient) {}

  getWorkTypes(): Observable<WorkType[]> {
    return this.http.get(
      `${environment.databaseApiURL}/work-types`
    ) as Observable<WorkType[]>;
  }

  getWorkTypeById(id: number): Observable<WorkType> {
    return this.http.get(
      `${environment.databaseApiURL}/work-types/${id}`
    ) as Observable<WorkType>;
  }
}
