import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketClient, SocketConfig} from "../../../services/socket.services";
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../../utils/guid"
import {SessionServices} from "../../../services/session.services";
import {SessionEnabledClient} from "../../../services/session.services";

@Component({
    selector: "io-panel",
    templateUrl: "emulator/monitor/io_panel/io_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class IoPanel implements OnDestroy, SessionEnabledClient
{
    private _socketClient:SocketClient;

    public program = "";

    public structured_program:string[] = [];

    public frequencyView = "";

    public instructionView = "";

    public pcRegView = "";

    public memoryView:string[] = [];

    public registerView:string[] = [];

    public outputView:string[] = [];

    public statusList:string[] = [];

    public errorList:string[] = [];

    public _sessionId = "";

    public onSubmit()
    {
        console.log("Upload Program");
        if (this.program != null && this.program != "")
        {
            this.structured_program = this.program.split("\n");
        }
        if (this.structured_program != null && this.structured_program.length != 0)
        {
            this._socketClient.push("/emulator_in/io",{
                        "sessionId": this._sessionId,
                        "program": this.structured_program
                    });
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

    public onPcRegResponse(response:Message)
    {
        this.pcRegView = JSON.parse(response.body);
    }

    public onOutputResponse(response:Message)
    {
        this.outputView = JSON.parse(response.body);
    }

    public onStatusResponse(response:Message)
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

    constructor()
    {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
    };

    configSocket():SocketConfig {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/io";
        config.subscribe("/emulator_response/io/instruction/{{client_id}}", (response:Message) => {this.onInstructionResponse(response)});
        config.subscribe("/emulator_response/io/memory/{{client_id}}", (response:Message) => {this.onMemoryResponse(response)});
        config.subscribe("/emulator_response/io/register/{{client_id}}", (response:Message) => {this.onRegisterResponse(response)});
        config.subscribe("/emulator_response/io/status/{{client_id}}", (response:Message) => {this.onStatusResponse(response)});
        config.subscribe("/emulator_response/io/pc_reg/{{client_id}}", (response:Message) => {this.onPcRegResponse(response)});
        config.subscribe("/emulator_response/io/output/{{client_id}}", (response:Message) => {this.onOutputResponse(response)});
        config.subscribe("/emulator_response/io/error/{{client_id}}", (response:Message) => {this.onError(response)});
        return config;
    }

    ngOnDestroy()
    {
        this._socketClient.disconnect();
    };
}
