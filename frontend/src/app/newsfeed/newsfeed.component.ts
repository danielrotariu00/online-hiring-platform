import { Component, OnInit } from "@angular/core";
import { NewsfeedEntry, User } from "src/app/models";
import { AccountService } from "src/app/services";
import { NewsfeedService } from "src/app/services/newsfeed.service";

@Component({
  selector: "app-newsfeed",
  templateUrl: "./newsfeed.component.html",
  styleUrls: ["./newsfeed.component.css"],
})
export class NewsfeedComponent implements OnInit {
  entries: NewsfeedEntry[] = [];
  user: User | null;
  maxEntries: number = 5;

  constructor(
    private newsfeedService: NewsfeedService,
    private accountService: AccountService
  ) {
    this.user = this.accountService.userValue;
  }

  ngOnInit(): void {
    this.newsfeedService
      .getNewsfeed(this.user.id, this.maxEntries)
      .subscribe((entries: NewsfeedEntry[]) => {
        this.entries = entries;
      });
  }

  onScroll(): void {
    this.newsfeedService
      .getNewsfeed(this.user.id, this.maxEntries)
      .subscribe((entries: NewsfeedEntry[]) => {
        this.entries.push(...entries);
      });
  }
}
