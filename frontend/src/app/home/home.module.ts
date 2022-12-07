import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";

import { HomeRoutingModule } from "./home-routing.module";
import { NewsfeedComponent } from "./newsfeed/newsfeed.component";

import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { InfiniteScrollModule } from "ngx-infinite-scroll";

@NgModule({
  declarations: [NewsfeedComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    CardModule,
    ButtonModule,
    HttpClientModule,
    InfiniteScrollModule,
  ],
})
export class HomeModule {}
