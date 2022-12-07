import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

import { environment } from "../../environments/environment";
import { Company } from "../_models";

@Injectable({
  providedIn: "root",
})
export class CompanyService {
  constructor(private http: HttpClient) {}

  getCompanies(): Observable<Company[]> {
    return this.http.get(
      `${environment.databaseApiURL}/companies`
    ) as Observable<Company[]>;
  }

  getCompanyById(id: number): Observable<Company> {
    return this.http.get(
      `${environment.databaseApiURL}/companies/${id}`
    ) as Observable<Company>;
  }
}
