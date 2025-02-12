import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { CompaniesComponent } from "./components/companies/companies.component";
import { CompanyDetailsComponent } from "./components/company-details/company-details.component";

import { AuthGuard } from "./helpers";
import { JobApplicationsComponent } from "./components/job-applications/job-applications.component";

import { JobsComponent } from "./components/jobs/jobs.component";
import { NewsfeedComponent } from "./components/newsfeed/newsfeed.component";
import { UserProfileComponent } from "./components/user-profile/user-profile.component";
import { NotificationsComponent } from "./components/notifications/notifications.component";
import { RecruiterHomeComponent } from "./components/recruiter-home/recruiter-home.component";
import { RecruiterJobApplicationsComponent } from "./components/recruiter-job-applications/recruiter-job-applications.component";
import { ManagerHomeComponent } from "./components/manager-home/manager-home.component";
import { CompanyRecruitersComponent } from "./components/company-recruiters/company-recruiters.component";
import { RecruiterGuard } from "./helpers/recruiter.guard";
import { ManagerGuard } from "./helpers/manager.guard";
import { CandidateGuard } from "./helpers/candidate.guard";

const accountModule = () =>
  import("./account/account.module").then((x) => x.AccountModule);

const routes: Routes = [
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "account", loadChildren: accountModule },
  {
    path: "home",
    component: NewsfeedComponent,
    canActivate: [CandidateGuard],
  },
  {
    path: "companies",
    component: CompaniesComponent,
    canActivate: [CandidateGuard],
  },
  {
    path: "companies/:id",
    component: CompanyDetailsComponent,
    canActivate: [CandidateGuard],
  },
  {
    path: "jobs",
    component: JobsComponent,
    canActivate: [CandidateGuard],
  },
  {
    path: "job-applications",
    component: JobApplicationsComponent,
    canActivate: [CandidateGuard],
  },
  {
    path: "users/:id",
    component: UserProfileComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "notifications",
    component: NotificationsComponent,
    canActivate: [AuthGuard],
  },
  // recruiter
  {
    path: "recruiter-home",
    component: RecruiterHomeComponent,
    canActivate: [RecruiterGuard],
  },
  {
    path: "recruiter-job-applications",
    component: RecruiterJobApplicationsComponent,
    canActivate: [RecruiterGuard],
  },
  // manager
  {
    path: "manager-home",
    component: ManagerHomeComponent,
    canActivate: [ManagerGuard],
  },

  {
    path: "company-recruiters",
    component: CompanyRecruitersComponent,
    canActivate: [ManagerGuard],
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
