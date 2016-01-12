import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketServices} from "../../../services/socket_services";
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../../utils/guid"

@Component({
    selector: "io-panel",
    templateUrl: "emulator/monitor/io_panel/io_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class IoPanel implements OnDestroy
{
    _host = "localhost:8080/";

    private _ioClient = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/io");

    private _handShakeClient = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");

    public program = "";

    public structured_program:string[] = [];

    public instructionView:string[] = [];

    public memoryView:string[] = [];

    public registerView:string[] = [];

    public status = "waiting";

    public error = "none";

    private _sessionId = "";

    private _handShakeTimer = null;

    private _handShakeCount = 0;

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
                this._ioClient.send("/emulator_in/io", {}, JSON.stringify({
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
        this.status = JSON.parse(response.body);
    };

    public onError(response:Message)
    {
        this.error = JSON.parse(response.body);
    };

    private connectIoSocket()
    {
        if (this._ioClient != null && !this._ioClient.connected)
        {
            try
            {
                this._ioClient.connect({},
                        (frame:Frame) =>
                        {
                            this._ioClient.subscribe("/emulator_response/instruction/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Instruction Response");
                                        this.onInstructionResponse(response);
                                    });
                            this._ioClient.subscribe("/emulator_response/memory/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Memory Response");
                                        this.onMemoryResponse(response);
                                    });
                            this._ioClient.subscribe("/emulator_response/register/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Register Response");
                                        this.onRegisterResponse(response);
                                    });
                            this._ioClient.subscribe("/emulator_response/error/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("IO Error Response");
                                        this.onError(response);
                                    });
                        });
                console.log("IO Client Connected");
            }
            catch (e)
            {
                console.error(e)
            }
        }
    };

    private connectHandShakeSocket()
    {
        if (this._handShakeClient != null && !this._handShakeClient.connected)
        {
            try
            {
                this._handShakeClient.connect({},
                        (frame:Frame) =>
                        {
                            this._handShakeClient.subscribe("/emulator_response/hand_shake/" + this._sessionId,
                                    (response:Message)=>
                                    {
                                        console.log("Hand Shake Response");
                                        this.onHandShakeResponse(response);
                                    });
                        });
                console.log("HandShake Connected");
            }
            catch (e)
            {
                console.error(e);
            }
        }
    };

    private disconnectHandShakeSocket()
    {
        clearInterval(this._handShakeTimer);
        if (this._handShakeClient != null && this._handShakeClient.connected)
        {
            this._handShakeClient.send("/emulator_in/hand_shake", {}, JSON.stringify({
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

    private keepSessionActive()
    {
        console.log("HandShake Start");
        if (this._handShakeClient != null && this._handShakeClient.connected)
        {
            this._handShakeCount = this._handShakeCount + 1;
            this._handShakeClient.send("/emulator_in/hand_shake", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "hello"
            }));
            console.log("HandShake Active:" + this._handShakeCount);
        }
        else
        {
            console.log(this._handShakeCount);
            clearInterval(this._handShakeTimer);
            this._handShakeCount = 0;
            this._handShakeClient = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");
            this.connectHandShakeSocket();
            this._handShakeTimer = setInterval(() =>
            {
                this.keepSessionActive()
            }, 5000);
        }
    }

    constructor()
    {
        var node = document.getElementById("session_id");
        if (node == null)
        {
            this._sessionId = Guid.newGuid();
        }
        else
        {
            this._sessionId = node.innerText;
            if (node.innerText == null || node.innerText == "")
            {
                this._sessionId = Guid.newGuid();
                node.innerText = this._sessionId;
            }
        }
        this.connectHandShakeSocket();
        this.connectIoSocket();
        console.log("Connect Complete");
        this._handShakeTimer = setInterval(() =>
        {
            this.keepSessionActive()
        }, 5000);
    };

    ngOnDestroy()
    {
        this.disconnectHandShakeSocket();
        this.disconnectIoSocket();
    };
}
