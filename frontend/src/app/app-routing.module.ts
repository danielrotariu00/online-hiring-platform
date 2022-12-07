import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { AuthGuard } from "./_helpers";

import { JobsComponent } from "./jobs/jobs.component";

const homeModule = () => import("./home/home.module").then((x) => x.HomeModule);
const accountModule = () =>
  import("./account/account.module").then((x) => x.AccountModule);
const usersModule = () =>
  import("./users/users.module").then((x) => x.UsersModule);

const routes: Routes = [
  { path: "", redirectTo: "home", pathMatch: "full" },
  { path: "home", loadChildren: homeModule, canActivate: [AuthGuard] },
  { path: "users", loadChildren: usersModule, canActivate: [AuthGuard] },
  { path: "account", loadChildren: accountModule },
  { path: "jobs", component: JobsComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
