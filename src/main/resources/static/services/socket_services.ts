import { Component, Injectable} from 'angular2/core';
import { Http, HTTP_PROVIDERS } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import {Client, Message, Frame} from 'stompjs';
//import {SockJs} from "sockjs";

declare var Stomp;

@Injectable()
export class SocketServices {
    public static clientFactory(url : string) {
        var webSocket = new WebSocket(url);
        return Stomp.over(webSocket);
        //return Stomp.client(url);
    }
}
