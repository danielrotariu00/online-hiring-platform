import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { City } from "../_models";

@Injectable({
  providedIn: "root",
})
export class CityService {
  constructor(private http: HttpClient) {}

  getCities(): Observable<City[]> {
    return this.http.get(`${environment.databaseApiURL}/cities`) as Observable<
      City[]
    >;
  }

  getCityById(id: number): Observable<City> {
    return this.http.get(
      `${environment.databaseApiURL}/cities/${id}`
    ) as Observable<City>;
  }
}
