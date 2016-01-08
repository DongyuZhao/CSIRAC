import { Component, Injectable} from 'angular2/core';
import { Http, HTTP_PROVIDERS } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import { Client, Message, Frame} from 'stompjs';

declare var Stomp;

@Injectable()
export class SocketServices {
    public static clientFactory(url : string) {
        var socket = new WebSocket(url);
        return Stomp.over(socket);
    }
}
