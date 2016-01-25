import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../utils/guid"
import {SocketClient} from "../services/socket.services";
import {SocketConfig} from "../services/socket.services";
import {SessionEnabledClient} from "../services/session.services";
import {SessionServices} from "../services/session.services";
import {HandshakeClient} from "./handshake/client";
import {MonitorClient} from "./monitor/client";
import {DebuggerClient} from "./debugger/client";

@Component({
    selector:"emulator-client",
    template:"<monitor></monitor><debugger></debugger><handshake></handshake>",
    directives: [HandshakeClient, MonitorClient, DebuggerClient]
})

export class EmulatorComponent{

}