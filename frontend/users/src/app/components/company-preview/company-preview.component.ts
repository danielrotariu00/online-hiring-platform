import { Component, Input, OnInit } from "@angular/core";
import { Company } from "../../models";

@Component({
  selector: "app-company-preview",
  templateUrl: "./company-preview.component.html",
  styleUrls: ["./company-preview.component.css"],
})
export class CompanyPreviewComponent implements OnInit {
  @Input()
  company: Company;
  constructor() {}

  ngOnInit(): void {
    if (this.company.photo) {
      let splitted = this.company.photo.split(":");
      splitted[1] = "//localhost";
      this.company.photo = splitted.join(":") + `?${Date.now()}`;
    }
  }
}
