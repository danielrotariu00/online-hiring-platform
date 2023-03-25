import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";

var SockJs = require("sockjs-client");
var Stomp = require("stompjs");

@Injectable({ providedIn: "root" })
export class WebSocketService {
  public connect() {
    let socket = new SockJs(
      `${environment.notificationApiURL}/ws-notifications`
    );

    let stompClient = Stomp.over(socket);

    return stompClient;
  }
}
