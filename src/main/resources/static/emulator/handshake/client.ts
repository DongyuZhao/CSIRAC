import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../utils/guid"
import {SocketClient} from "../../services/socket.services";
import {SocketConfig} from "../../services/socket.services";
import {SessionEnabledClient} from "../../services/session.services";
import {SessionServices} from "../../services/session.services";


@Component({
    selector: "handshake",
    templateUrl: "emulator/handshake/client.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class HandshakeClient implements OnDestroy, SessionEnabledClient {
    private _socketClient:SocketClient;

    public statusList:string[] = [];

    public errorList:string[] = [];

    public _sessionId = "";

    private _Timer = null;

    public onResponse(response:Message) {
        if (this.statusList.length >= 5) {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    };

    public onError(response:Message) {
        if (this.errorList.length >= 5) {
            this.errorList = [];
        }
        this.errorList.push(JSON.parse(response.body));
    };

    private keepSessionActive() {
        try {
            this._socketClient.push("/emulator_in/handshake",
                {
                    "sessionId": this._sessionId,
                    "operation": "hello"
                });
        }
        catch (e) {
            clearInterval(this._Timer);
            this._socketClient = new SocketClient(this.configSocket());
            this._socketClient.connect();
            this._Timer = setInterval(() => {
                this.keepSessionActive()
            }, 5000);
        }
    }

    constructor() {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
        setTimeout(() => {
            this.keepSessionActive()
        }, 1000);
        this._Timer = setInterval(() => {
            this.keepSessionActive()
        }, 5000);
    };

    configSocket():SocketConfig {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/handshake";
        config.subscribe("/emulator_response/handshake/{{client_id}}", (response:Message) => {
            this.onResponse(response)
        });
        config.subscribe("/emulator_response/handshake/error/{{client_id}}", (response:Message) => {
            this.onError(response)
        });
        console.log(config.subscribeMap);
        return config;
    }

    ngOnDestroy() {
        this._socketClient.disconnect();
    };
}
