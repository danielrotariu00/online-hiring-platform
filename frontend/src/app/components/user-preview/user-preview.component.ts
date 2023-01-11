import { Component, OnInit, Input } from "@angular/core";
import { DatabaseService } from "src/app/services";
import { UserDetails } from "../../models";

@Component({
  selector: "app-user-preview",
  templateUrl: "./user-preview.component.html",
  styleUrls: ["./user-preview.component.css"],
})
export class UserPreviewComponent implements OnInit {
  @Input()
  userId: number;

  userDetails: UserDetails;

  constructor(private databaseService: DatabaseService) {}

  ngOnInit(): void {
    this.databaseService
      .getUserDetails(this.userId)
      .subscribe((userDetails: UserDetails) => {
        this.userDetails = userDetails;
      });
  }
}
