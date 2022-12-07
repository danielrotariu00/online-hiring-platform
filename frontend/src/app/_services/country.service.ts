import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { Country } from "../_models/country";

@Injectable({
  providedIn: "root",
})
export class CountryService {
  constructor(private http: HttpClient) {}

  getCountries(): Observable<Country[]> {
    return this.http.get(
      `${environment.databaseApiURL}/countries`
    ) as Observable<Country[]>;
  }

  getCountryByID(id: number): Observable<Country> {
    return this.http.get(
      `${environment.databaseApiURL}/countries/${id}`
    ) as Observable<Country>;
  }
}
