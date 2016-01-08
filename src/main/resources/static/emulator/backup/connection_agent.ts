import {Guid} from "../../utils/guid"

import { Component, OnInit, OnDestroy } from '../../node_modules/angular2/core.d';
import { Http, HTTP_PROVIDERS } from '../../node_modules/angular2/http.d';
import { Observable } from '../../node_modules/rxjs/Observable.d';
import {Stomp, Client, Message, Frame} from 'stompjs';

export class ConnectionAgent{
    public sessionId = Guid.newGuid();

    private _connectionSockets : string[] = [];

    private _handShakeClient : Client = null;
    private _ioClient : Client  = null;
    private _controlClient : Client = null;

    private handShakeConnectUrl = "emulator_in/hand_shake";
    private handShakeSubscribeUrl = "emulator_response/hand_shake";

    private ioConnectUrl = "emulator_in/io";
    private ioSubscribeUrl = "emulator_response/io";

    private controlConnectUrl = "emulator_in/control";
    private controlSubscribeUrl = "emulator_response/control";

    private static getSafeString(src: string) : string {
        var result = "";
        if (src != null) {
            result = src;
        }
        return result;
    }

    private static buildSubscribeUrl(baseUrl : string, topic? : string, sessionId? : string) : string {
        var safeBaseUrl = ConnectionAgent.getSafeString(baseUrl);
        var safeTopic = ConnectionAgent.getSafeString(topic);
        var safeSessionId = ConnectionAgent.getSafeString(sessionId);
        if (safeBaseUrl.length != 0 && safeBaseUrl.charAt(safeBaseUrl.length - 1) != '/') {
            safeBaseUrl += '/';
        }
        if (safeTopic.length != 0 && safeTopic.charAt(safeBaseUrl.length - 1) != '/') {
            safeBaseUrl += '/';
        }
        return safeBaseUrl + safeTopic + safeSessionId
    }

    constructor(public onHandShakeResponse : (response: Message) => any,
                public onMemoryUpdate : (response: Message) => any,
                public onRegisterUpdate : (response: Message) => any,
                public onCurrentInstructionUpdate : (response: Message) => any,
                public onError : (response: Message) => any ) {
        this.connect();
    }

    public handShake() {
        if (this._handShakeClient != null) {
            this._handShakeClient.send(this.handShakeConnectUrl, {} , JSON.stringify({
                "sessionId": this.sessionId,
                "operation": "hello"
            }));
        }
        else {
            console.error("HandShake Client Null");
        }
    }

    public uploadProgram(program:string[]) {
        if (program != null) {
            if (this._ioClient != null) {
                this._ioClient.send(this.ioConnectUrl, {}, JSON.stringify({
                    "sessionId": this.sessionId,
                    "program": program
                }))
            }
            else {
                console.error("IO Client Null");
            }
        }
        else {
            console.error("Program Null");
        }
    }

    public startRunning() {
        if (this._controlClient != null) {
            this._controlClient.send(this.controlConnectUrl, {} , JSON.stringify({
                "sessionId": this.sessionId,
                "operation": "start"
            }));
        }
        else {
            console.error("Control Client Null");
        }
    }

    public pauseRunning() {
        if (this._controlClient != null) {
            this._controlClient.send(this.controlConnectUrl, {} , JSON.stringify({
                "sessionId": this.sessionId,
                "operation": "pause"
            }));
        }
        else {
            console.error("Control Client Null");
        }
    }

    public stopRunning() {
        if (this._controlClient != null) {
            this._controlClient.send(this.controlConnectUrl, {} , JSON.stringify({
                "sessionId": this.sessionId,
                "operation": "stop"
            }));
        }
        else {
            console.error("Control Client Null");
        }
    }

    private addConnectionSocket(socketName: string) {
        if (this._connectionSockets.indexOf(socketName) < 0) {
            this._connectionSockets.push(socketName);
        }
    }

    private socketConnectionExist(socketName: string) {
        return (this._connectionSockets.indexOf(socketName) >= 0)
    }

    private connectHandShakeSocket() {
        if (!this.socketConnectionExist("hand_shake")) {
            var socket = new WebSocket(this.handShakeConnectUrl);
            this._handShakeClient = Stomp.over(socket);
            this._handShakeClient.connect(
                {},
                (frame: Frame) => {
                    console.log("Connecting HandShake Socket");
                    this._handShakeClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.handShakeSubscribeUrl, null, this.sessionId),
                        (response: Message) => {
                            console.log("HandShake Response Call Back");
                            this.onHandShakeResponse(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("hand_shake");
                });
        }
    }

    private connectIoSocket() {
        if (!this.socketConnectionExist("io")) {
            var socket = new WebSocket(this.ioConnectUrl);
            this._ioClient = Stomp.over(socket);
            this._ioClient.connect(
                {},
                (frame: Frame) => {
                    this.addConnectionSocket("io");
                    console.log("Connecting I/O Socket");
                    this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.ioSubscribeUrl, "memory" , this.sessionId),
                        (response: Message) => {
                            console.log("Memory Update Call Back");
                            this.onMemoryUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("memory");


                    this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.ioSubscribeUrl, "register" , this.sessionId),
                        (response: Message) => {
                            console.log("Register Update Call Back");
                            this.onRegisterUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("register");

                    this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.ioSubscribeUrl, "instruction" , this.sessionId),
                        (response: Message) => {
                            console.log("Current Instruction Update Call Back");
                            this.onCurrentInstructionUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("instruction");

                    this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.ioSubscribeUrl, "error" , this.sessionId),
                        (response: Message) => {
                            console.log("IO Error Call Back");
                            this.onError(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("error");
                });
        }
    }

    private connectControlSocket() {
        if (!this.socketConnectionExist("control")) {
            var socket = new WebSocket(this.controlConnectUrl)
            this._controlClient = Stomp.over(socket);
            this._controlClient.connect(
                {},
                (frame: Frame) => {
                    console.log("Connecting Control Socket");
                    this.addConnectionSocket("control");
                    this._controlClient.subscribe(ConnectionAgent.buildSubscribeUrl(this.controlSubscribeUrl, "error" , this.sessionId),
                        (response: Message) => {
                            console.log("Control Error Call Back");
                            this.onError(JSON.parse(response.body));
                        });
                });
        }
    }

    public connect() {
        this.connectHandShakeSocket();
        this.connectIoSocket();
        this.connectControlSocket();
    }

    public disconnect() {
        if (this._controlClient != null) {
            this._controlClient.disconnect(null);
            this._controlClient = null;
            console.log("Control Client Disconnect");
        }
        if (this._ioClient != null) {
            this._ioClient.disconnect(null);
            this._ioClient = null;
            console.log("IO Client Disconnect");
        }
        if (this._handShakeClient != null) {
            this._handShakeClient.send(this.handShakeConnectUrl, {} , JSON.stringify(
                {
                    'sessionId': this.sessionId,
                    'operation': 'bye'
                }
            ));
            this._handShakeClient.disconnect(null);
            this._handShakeClient = null;
            console.log("HandShake Client Disconnect");
        }
        this._connectionSockets = [];
    }

    public reset() {
        this.disconnect();
        this.connect();
    }
}
