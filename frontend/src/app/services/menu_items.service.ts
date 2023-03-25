import { Injectable } from "@angular/core";
import { MenuItem } from "primeng/api";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class MenuItemsService {
  private menuItems: BehaviorSubject<MenuItem[]> = new BehaviorSubject<
    MenuItem[]
  >(null);
  public menuItems$: Observable<MenuItem[]> = this.menuItems.asObservable();

  updateMenuItems(updatedItems) {
    this.menuItems.next(updatedItems);
  }
}
