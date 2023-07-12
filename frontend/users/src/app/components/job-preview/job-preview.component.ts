import { Component, OnInit, Input } from "@angular/core";
import { Job } from "../../models";

@Component({
  selector: "app-job-preview",
  templateUrl: "./job-preview.component.html",
  styleUrls: ["./job-preview.component.css"],
})
export class JobPreviewComponent implements OnInit {
  @Input()
  job: Job;

  constructor() {}

  ngOnInit(): void {
    if (this.job.company.photo) {
      let splitted = this.job.company.photo.split(":");
      splitted[1] = "//localhost";
      this.job.company.photo = splitted.join(":") + `?${Date.now()}`;
    }
  }
}
