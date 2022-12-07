import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HttpClientModule, HTTP_INTERCEPTORS } from "@angular/common/http";
import { MenubarModule } from "primeng/menubar";
import { InputTextModule } from "primeng/inputtext";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { MultiSelectModule } from "primeng/multiselect";

// used to create fake backend
import { fakeBackendProvider } from "./_helpers";

import { AppRoutingModule } from "./app-routing.module";
import { JwtInterceptor, ErrorInterceptor } from "./_helpers";
import { AppComponent } from "./app.component";
import { AlertComponent } from "./_components";
import { MenubarComponent } from "./menubar/menubar.component";
import { JobsComponent } from "./jobs/jobs.component";
import { JobDetailsComponent } from './job-details/job-details.component';

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
    ButtonModule,
    CardModule,
    MultiSelectModule,
  ],
  declarations: [AppComponent, AlertComponent, MenubarComponent, JobsComponent, JobDetailsComponent],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

    // provider used to create fake backend
    fakeBackendProvider,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
