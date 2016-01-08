import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketServices} from "../../../services/socket_services";
import {HTTP_PROVIDERS} from "angular2/http";

@Component({
    selector: "io-panel",
    templateUrl: "emulator/monitor/io_panel/io_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class IoPanel implements OnDestroy
{

    private _ioClient = SocketServices.clientFactory("emulator_in/io");

    private _handShakeClient = SocketServices.clientFactory("emulator_in/hand_shake");

    public program = "";

    public structured_program:string[] = [];

    public instructionView:string[] = [];

    public memoryView:string[] = [];

    public registerView:string[] = [];

    public statue = "";

    public error = "";

    private _sessionId = "";

    public onSubmit()
    {
        if (this.program != null && this.program != "")
        {
            this.structured_program = this.program.split("\n");
        }
        if (this.structured_program != null && this.structured_program.length != 0)
        {
            if (this._ioClient != null && this._ioClient.connected)
            {
                this._ioClient.send("emulator_in/io", {}, JSON.stringify({
                    "sessionId": this._sessionId,
                    "program": this.structured_program
                }));
            }
            else
            {
                console.error("IO Client Null or UnConnected");
            }
        }
        else
        {
            console.error("Program Null");
        }
    };

    public onInstructionResponse(response:Message)
    {
        this.instructionView = JSON.parse(response.body).content;
    };

    public onMemoryResponse(response:Message)
    {
        this.memoryView = JSON.parse(response.body).content;
    };

    public onRegisterResponse(response:Message)
    {
        this.registerView = JSON.parse(response.body).content;
    };

    public onHandShakeResponse(response:Message)
    {
        this.statue = JSON.parse(response.body).content;
    };

    public onError(response:Message)
    {
        this.error = JSON.parse(response.body).content;
    };

    private connectIoSocket()
    {
        if (this._ioClient != null && !this._ioClient.connected)
        {
            this._ioClient.connect({},
                    (frame:Frame) =>
                    {
                        this._ioClient.subscribe("emulator_response/instruction/" + this._sessionId,
                                (response:Message) =>
                                {
                                    console.log("Instruction Response");
                                    this.onInstructionResponse(response);
                                });
                        this._ioClient.subscribe("emulator_response/memory/" + this._sessionId,
                                (response:Message) =>
                                {
                                    console.log("Memory Response");
                                    this.onMemoryResponse(response);
                                });
                        this._ioClient.subscribe("emulator_response/register/" + this._sessionId,
                                (response:Message) =>
                                {
                                    console.log("Register Response");
                                    this.onRegisterResponse(response);
                                });
                        this._ioClient.subscribe("emulator_response/error/" + this._sessionId,
                                (response:Message) =>
                                {
                                    console.log("IO Error Response");
                                    this.onError(response);
                                });
                    });
        }
    };

    private connectHandShakeSocket()
    {
        if (this._handShakeClient != null && !this._handShakeClient.connected)
        {
            this._handShakeClient.connect({},
                    (frame:Frame) =>
                    {
                        this._handShakeClient.subscribe("emulator_response/hand_shake/" + this._sessionId,
                                (response:Message)=>
                                {
                                    console.log("Hand Shake Response");
                                    this.onHandShakeResponse(response);
                                });
                    });
        }
    };

    private disconnectHandShakeSocket()
    {
        if (this._handShakeClient != null && this._handShakeClient.connected)
        {
            this._handShakeClient.send("emulator_in/hand_shake", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "bye"
            }));
            this._handShakeClient.disconnect();
            console.log("HandShake Client Disconnect");
        }
    };

    private disconnectIoSocket()
    {
        if (this._ioClient != null && this._ioClient.connected)
        {
            this._ioClient.disconnect();
            console.log("IO Client Disconnect");
        }
    };

    private keepAlive() {
        if (this._handShakeClient != null && this._handShakeClient.connected)
        {
            this._handShakeClient.send("emulator_in/hand_shake", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "hello"
            }));
            console.log("HandShake Alive");
        }
    }

    constructor()
    {
        this._sessionId = document.getElementById("session_id").innerText;
        this.connectHandShakeSocket();
        this.connectIoSocket();
    };

    ngOnDestroy()
    {
        this.disconnectHandShakeSocket();
        this.disconnectIoSocket();
    };
}