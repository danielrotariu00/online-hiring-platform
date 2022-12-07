import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { NewsfeedComponent } from "./newsfeed/newsfeed.component";

const routes: Routes = [
  { path: "", component: NewsfeedComponent, pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {}
