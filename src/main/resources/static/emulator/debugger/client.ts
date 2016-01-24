import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../utils/guid"
import {SocketClient} from "../../services/socket.services";
import {SocketConfig} from "../../services/socket.services";
import {SessionEnabledClient} from "../../services/session.services";
import {SessionServices} from "../../services/session.services";


@Component({
    selector: "debugger",
    templateUrl: "emulator/debugger/client.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class DebuggerClient implements OnDestroy {

    private _socketClient:SocketClient;

    public pcRegister = 0;
    public opCode = 0;
    public clock = 1;

    public DataUpdateHistory = [];
    public ProgramUpdateHistory = [];
    public RegisterUpdateHistory = [];

    public statusList:string[] = [];

    public errorList:string[] = [];

    public _sessionId = "";

    public onFrequencySubmit() {
        this._socketClient.push("/emulator_in/debugger/input/clock", {
            "sessionId": this._sessionId,
            "frequency": this.clock
        });
    };

    public onPcRegisterSubmit() {
        this._socketClient.push("/emulator_in/debugger/input/pc_reg", {
            "sessionId": this._sessionId,
            "address": this.pcRegister
        });
    };

    public onInstructionResponse(response:Message) {
        this.opCode = JSON.parse(response.body);
    };

    public onDataMemoryResponse(response:Message) {
        var result = JSON.parse(response.body)
        this.DataUpdateHistory.push("Unit:" + result.groupAddress + "Cell:" + result.cellAddress + ":" + result.newValue);
    };

    public onProgramMemoryResponse(response:Message) {
        this.ProgramUpdateHistory.push(JSON.parse(response.body));
    };

    public onRegisterResponse(response:Message) {
        this.RegisterUpdateHistory.push(JSON.parse(response.body));
    };

    public onPcRegResponse(response:Message) {
        this.pcRegister = JSON.parse(response.body);
    }

    public onClockResponse(response:Message) {
        this.clock = JSON.parse(response.body);
    }

    // public onOutputResponse(response:Message)
    // {
    //     this.outputView = JSON.parse(response.body);
    // }

    public onStatusResponse(response:Message) {
        if (this.statusList.length > 5) {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    }

    public onError(response:Message) {
        this.errorList.push(JSON.parse(response.body));
    };

    constructor() {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
    };

    configSocket():SocketConfig {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/debugger";
        config.subscribe("/emulator_response/debugger/opcode/{{client_id}}", (response:Message) => {
            this.onInstructionResponse(response)
        });
        config.subscribe("/emulator_response/debugger/data_memory/{{client_id}}", (response:Message) => {
            this.onDataMemoryResponse(response)
        });
        config.subscribe("/emulator_response/debugger/program_memory/{{client_id}}", (response:Message) => {
            this.onProgramMemoryResponse(response)
        });
        config.subscribe("/emulator_response/debugger/register/{{client_id}}", (response:Message) => {
            this.onRegisterResponse(response)
        });
        config.subscribe("/emulator_response/debugger/status/{{client_id}}", (response:Message) => {
            this.onStatusResponse(response)
        });
        config.subscribe("/emulator_response/debugger/pc_reg/{{client_id}}", (response:Message) => {
            this.onPcRegResponse(response)
        });
        config.subscribe("/emulator_response/debugger/clock/{{client_id}}", (response:Message) => {
            this.onClockResponse(response)
        });
        config.subscribe("/emulator_response/debugger/error/{{client_id}}", (response:Message) => {
            this.onError(response)
        });
        return config;
    }

    ngOnDestroy() {
        this._socketClient.disconnect();
    };
}

