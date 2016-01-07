import {Guid} from "../../utils/guid"

import { Component, OnInit, OnDestroy } from 'angular2/core';
import { Http, HTTP_PROVIDERS } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import {Stomp, Client, Message, Frame} from 'stompjs';



export class ConnectionAgent{
    public sessionId = Guid.newGuid();

    private _connectionSockets : string[] = [];

// TODO::Add callback for hand shake response
    public onHandShakeResponse(message: any) {

    }

// TODO::Add callback for memory response
    public onMemoryUpdate(message: any) {

    }

// TODO::Add callback for register response
    public onRegisterUpdate(message: any) {

    }

// TODO::Add callback for instruction response
    public onCurrentInstructionUpdate(message: any) {

    }

// TODO::Add callback for error response
    public onError(message: any) {

    }

    constructor(private _handShakeClient : Client, private _ioClient : Client, private _controlClient : Client ) {
        this.connect();
    }

/**
 * addConnectionSocket
 *      Add the socket into the connected list if it is not exist in the list.
 * socketName:string    name of the socket
 */
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
            var socket = new WebSocket("emulator_in/hand_shake");
            this._handShakeClient = Stomp.over(socket);
            this._handShakeClient.connect(
                {},
                (frame: Frame) => {
                    console.log("Connecting HandShake Socket");
                    this._handShakeClient.subscribe('emulator_response/hand_shake/' + this.sessionId,
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
            var socket = new WebSocket("emulator_in/io");
            this._ioClient = Stomp.over(socket);
            this._ioClient.connect(
                {},
                (frame: Frame) => {
                    this.addConnectionSocket("io");
                    console.log("Connecting I/O Socket");
                    this._ioClient.subscribe('emulator_response/memory/' + this.sessionId,
                        (response: Message) => {
                            console.log("Memory Update Call Back");
                            this.onMemoryUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("memory");


                    this._ioClient.subscribe('emulator_response/register/' + this.sessionId,
                        (response: Message) => {
                            console.log("Register Update Call Back");
                            this.onRegisterUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("register");

                    this._ioClient.subscribe('emulator_response/instruction/' + this.sessionId,
                        (response: Message) => {
                            console.log("Current Instruction Update Call Back");
                            this.onCurrentInstructionUpdate(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("instruction");

                    this._ioClient.subscribe('emulator_response/error/' + this.sessionId,
                        (response: Message) => {
                            console.log("Error Call Back");
                            this.onError(JSON.parse(response.body));
                        });
                    this.addConnectionSocket("error");
                });
        }
    }

    private connectControlSocket() {
        if (!this.socketConnectionExist("control")) {
            var socket = new WebSocket("emulator_in/control")
            this._controlClient = Stomp.over(socket);
            this._controlClient.connect(
                {},
                (frame: Frame) => {
                    console.log("Connecting Control Socket");
                    this.addConnectionSocket("control");
                    this._controlClient.subscribe('emulator_response/error' + this.sessionId,
                        (response: Message) => {
                            console.log("Error Call Back");
                            this.onError(JSON.parse(response.body));
                        });
                });
        }
    }

    private connect() {
        this.connectHandShakeSocket();
        this.connectIoSocket();
        this.connectControlSocket();
    }

    private disconnect() {
        if (this._controlClient != null) {
            this._controlClient.disconnect(null);
            console.log("Control Client Disconnect");
        }
        if (this._ioClient != null) {
            this._ioClient.disconnect(null);
            console.log("IO Client Disconnect");
        }
        if (this._handShakeClient != null) {
            this._handShakeClient.send("/emulator_in/hand_shake", {} , JSON.stringify(
                {
                    'sessionId': this.sessionId,
                    'operation': 'bye'
                }
            ));
            this._handShakeClient.disconnect(null);
            console.log("HandShake Client Disconnect");
        }
        this._connectionSockets = [];
    }

    private reset() {
        this.disconnect();
        this.connect();
    }
}
