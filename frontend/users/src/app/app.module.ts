import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MenubarModule } from "primeng/menubar";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from "primeng/inputtextarea";
import { CardModule } from "primeng/card";
import { MultiSelectModule } from "primeng/multiselect";
import { PaginatorModule } from "primeng/paginator";
import { OrderListModule } from "primeng/orderlist";
import { FieldsetModule } from "primeng/fieldset";
import { ConfirmDialogModule } from "primeng/confirmdialog";
import { DialogModule } from "primeng/dialog";
import { ToastModule } from "primeng/toast";
import { RippleModule } from "primeng/ripple";
import { CalendarModule } from "primeng/calendar";
import { SplitButtonModule } from "primeng/splitbutton";
import { FileUploadModule } from "primeng/fileupload";
import { SliderModule } from "primeng/slider";

import { AppRoutingModule } from "./app-routing.module";
import { JwtInterceptor, ErrorInterceptor } from "./helpers";
import { AppComponent } from "./app.component";
import { MenubarComponent } from "./components/menubar/menubar.component";
import { NewsfeedComponent } from "./components/newsfeed/newsfeed.component";
import { AlertComponent } from "./components/alert/alert.component";
import { JobsComponent } from "./components/jobs/jobs.component";
import { JobDetailsComponent } from "./components/job-details/job-details.component";
import { JobApplicationsComponent } from "./components/job-applications/job-applications.component";
import { JobPreviewComponent } from "./components/job-preview/job-preview.component";
import { JobApplicationDetailsComponent } from "./components/job-application-details/job-application-details.component";
import { CompaniesComponent } from "./components/companies/companies.component";
import { CompanyPreviewComponent } from "./components/company-preview/company-preview.component";
import { CompanyDetailsComponent } from "./components/company-details/company-details.component";
import { UserProfileComponent } from "./components/user-profile/user-profile.component";
import { NotificationsComponent } from "./components/notifications/notifications.component";
import { RecruiterHomeComponent } from "./components/recruiter-home/recruiter-home.component";
import { RecruiterJobApplicationsComponent } from "./components/recruiter-job-applications/recruiter-job-applications.component";
import { UserPreviewComponent } from "./components/user-preview/user-preview.component";
import { MessageService } from "primeng/api";
import { ManagerHomeComponent } from "./components/manager-home/manager-home.component";
import { CompanyRecruitersComponent } from "./components/company-recruiters/company-recruiters.component";
import { SecurePipe } from "./helpers/secure.pipe";
import { ProgressSpinnerModule } from "primeng/progressspinner";

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    MenubarModule,
    InputTextModule,
    InputTextareaModule,
    CardModule,
    MultiSelectModule,
    PaginatorModule,
    OrderListModule,
    FieldsetModule,
    ConfirmDialogModule,
    DialogModule,
    ToastModule,
    RippleModule,
    CalendarModule,
    SplitButtonModule,
    FileUploadModule,
    SliderModule,
    ProgressSpinnerModule,
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
    UserProfileComponent,
    NotificationsComponent,
    RecruiterHomeComponent,
    RecruiterJobApplicationsComponent,
    UserPreviewComponent,
    ManagerHomeComponent,
    CompanyRecruitersComponent,
    SecurePipe,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    MessageService,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
