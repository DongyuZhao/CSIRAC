import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketServices} from "../../../services/socket_services";
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../../utils/guid"
import {MonitorComponent} from "../monitor.component";

@Component({
    selector: "io-panel",
    templateUrl: "emulator/monitor/io_panel/io_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class IoPanel implements OnDestroy
{
    _host = "localhost:8080/";

    private _client = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/io");

    public program = "";

    public structured_program:string[] = [];

    public instructionView:string[] = [];

    public memoryView:string[] = [];

    public registerView:string[] = [];

    public errorList = [];

    private _sessionId = "";

    private _retryCount = 0;

    public statusList = [];

    public onSubmit()
    {
        console.log("Upload Program");
        if (this.program != null && this.program != "")
        {
            this.structured_program = this.program.split("\n");
        }
        if (this.structured_program != null && this.structured_program.length != 0)
        {
            if (this._client != null && this._client.connected)
            {
                this._client.send("/emulator_in/io", {}, JSON.stringify({
                    "sessionId": this._sessionId,
                    "program": this.structured_program
                }));
                console.log("Upload Finished");
            }
            else
            {
                if (this._retryCount < 3)
                {
                    this.connect();
                    this._client.send("/emulator_in/io", {}, JSON.stringify({
                        "sessionId": this._sessionId,
                        "program": this.structured_program
                    }));
                }
                else
                {
                    this.errorList.push("No Connection");
                }
            }
        }
        else
        {
            console.error("Program Null");
        }
    };

    public onInstructionResponse(response:Message)
    {
        this.instructionView = JSON.parse(response.body);
    };

    public onMemoryResponse(response:Message)
    {
        this.memoryView = JSON.parse(response.body);
    };

    public onRegisterResponse(response:Message)
    {
        this.registerView = JSON.parse(response.body);
    };

    public onResponse(response:Message)
    {
        if (this.statusList.length > 5)
        {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    }

    public onError(response:Message)
    {
        this.errorList.push(JSON.parse(response.body));
    };

    private connect()
    {
        if (this._client != null && !this._client.connected)
        {
            try
            {
                this._client.connect({},
                        (frame:Frame) =>
                        {
                            this._client.subscribe("/emulator_response/instruction/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Instruction Response");
                                        this.onInstructionResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/memory/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Memory Response");
                                        this.onMemoryResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/register/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Register Response");
                                        this.onRegisterResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("IO Response");
                                        this.onResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/error/" + this._sessionId,
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

    private disconnect()
    {
        if (this._client != null && this._client.connected)
        {
            this._client.disconnect();
            console.log("IO Client Disconnect");
        }
    };

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
        this.connect();
    };

    ngOnDestroy()
    {
        this.disconnect();
    };
}
