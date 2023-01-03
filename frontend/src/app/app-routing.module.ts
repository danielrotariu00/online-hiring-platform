import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CompaniesComponent } from "./companies/companies.component";
import { CompanyDetailsComponent } from "./company-details/company-details.component";

import { AuthGuard } from "./helpers";
import { JobApplicationsComponent } from "./job-applications/job-applications.component";

import { JobsComponent } from "./jobs/jobs.component";
import { NewsfeedComponent } from "./newsfeed/newsfeed.component";

const accountModule = () =>
  import("./account/account.module").then((x) => x.AccountModule);

const routes: Routes = [
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "account", loadChildren: accountModule },
  { path: "home", component: NewsfeedComponent, canActivate: [AuthGuard] },
  {
    path: "companies",
    component: CompaniesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "companies/:id",
    component: CompanyDetailsComponent,
    canActivate: [AuthGuard],
  },
  { path: "jobs", component: JobsComponent, canActivate: [AuthGuard] },
  {
    path: "job-applications",
    component: JobApplicationsComponent,
    canActivate: [AuthGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
