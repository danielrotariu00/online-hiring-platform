import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { InfiniteScrollModule } from "ngx-infinite-scroll";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MenubarModule } from "primeng/menubar";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { MultiSelectModule } from "primeng/multiselect";
import { PaginatorModule } from "primeng/paginator";
import { OrderListModule } from "primeng/orderlist";
import { FieldsetModule } from "primeng/fieldset";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { ToastModule } from "primeng/toast";
import { RippleModule } from "primeng/ripple";

import { AppRoutingModule } from "./app-routing.module";
import { JwtInterceptor, ErrorInterceptor } from "./helpers";
import { AppComponent } from "./app.component";
import { MenubarComponent } from "./menubar/menubar.component";
import { NewsfeedComponent } from "./newsfeed/newsfeed.component";
import { AlertComponent } from "./alert/alert.component";
import { JobsComponent } from "./jobs/jobs.component";
import { JobDetailsComponent } from "./job-details/job-details.component";
import { JobApplicationsComponent } from "./job-applications/job-applications.component";
import { JobPreviewComponent } from "./job-preview/job-preview.component";
import { JobApplicationDetailsComponent } from "./job-application-details/job-application-details.component";
import { CompaniesComponent } from "./companies/companies.component";
import { CompanyPreviewComponent } from "./company-preview/company-preview.component";
import { CompanyDetailsComponent } from "./company-details/company-details.component";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    InfiniteScrollModule,
    HttpClientModule,
    AppRoutingModule,
    MenubarModule,
    InputTextModule,
    ButtonModule,
    CardModule,
    MultiSelectModule,
    PaginatorModule,
    OrderListModule,
    FieldsetModule,
    ConfirmDialogModule,
    ToastModule,
    RippleModule,
  ],
  declarations: [
    AppComponent,
    AlertComponent,
    MenubarComponent,
    NewsfeedComponent,
    JobsComponent,
    JobDetailsComponent,
    JobApplicationsComponent,
    JobPreviewComponent,
    JobApplicationDetailsComponent,
    CompaniesComponent,
    CompanyPreviewComponent,
    CompanyDetailsComponent,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
