import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../utils/guid"
import {SocketClient} from "../../services/socket.services";
import {SocketConfig} from "../../services/socket.services";
import {SessionEnabledClient} from "../../services/session.services";
import {SessionServices} from "../../services/session.services";


@Component({
    selector: "monitor",
    templateUrl: "emulator/monitor/client.html",
    viewProviders: [HTTP_PROVIDERS]
})


export class MonitorClient implements OnDestroy {

    private _socketClient:SocketClient;

    public _sessionId = "";

    public statusList:string[] = [];

    public errorList:string[] = [];

    public program = "0 0 M A\n0 0 M B\n0 0 M C\n0 1 A M\n0 2 B M\n0 3 C M";

    //public structured_program:string[] = [];

    public data = "1 1 1 1";

    //public structured_data:String[] = [];

    private _allowStart = false;

    private _allowPause = false;

    private _allowContinue = false;

    private _allowNext = false;

    private _allowStop = false;

    private _dataUploaded = false;

    private _programUploaded = false;

    private _checkTimer;

    private getLastError() {
        return this.errorList[this.errorList.length - 1];
    }

    private getLastStatus():string {
        return this.statusList[this.statusList.length - 1];
    }

    private onStatusResponse(response:Message) {
        this.statusList.push(JSON.parse(response.body));
        switch (this.getLastStatus()) {
            case "Pause":
                this._allowContinue = true;
                this._allowStart = false;
                this._allowPause = false;
                this._allowNext = true;
                this._allowStop = true;
                break;
            case "Running":
                this._allowContinue = false;
                this._allowStart = false;
                this._allowNext = false;
                this._allowPause = true;
                this._allowStop = true;
                break;
            case "Stop":
                this._allowContinue = false;
                this._allowNext = false;
                this._allowPause = false;
                this._allowStart = true;
                this._allowStop = false;
                break;
            case "Ready":
                this._allowContinue = false;
                this._allowNext = false;
                this._allowPause = false;
                this._allowStart = true;
                this._allowStop = false;
                break;
            case "Data Loaded":
                this._dataUploaded = true;
                break;
            case "Program Loaded":
                this._programUploaded = true;
                break;
            default:
                this._allowContinue = false;
                this._allowNext = false;
                this._allowPause = false;
                this._allowStart = false;
                this._allowStop = false;
                break;
        }
    }

    private onError(response:Message) {
        if (this.errorList.length >= 10) {
            this.errorList = [];
        }
        this.errorList = JSON.parse(response.body);
    }

    public onDataSubmit() {
        console.log("Upload Data");
        var structuredData = [];
        if (this.data != null && this.data != "") {
            structuredData = this.data.split("\n");
        }
        if (structuredData != null && structuredData.length != 0) {
            this._socketClient.push("/emulator_in/monitor/input/data", {
                "sessionId": this._sessionId,
                "data": structuredData
            });
        }
        else {
            console.error("Data Null");
        }
    }

    public onProgramSubmit() {
        console.log("Upload Program");
        var structuredProgram = [];
        if (this.program != null && this.program != "") {
            structuredProgram = this.program.split("\n");
        }
        if (structuredProgram != null && structuredProgram.length != 0) {
            this._socketClient.push("/emulator_in/monitor/input/program", {
                "sessionId": this._sessionId,
                "program": structuredProgram
            });
        }
        else {
            console.error("Program Null");
        }
    }

    public onStart() {
        if (this._dataUploaded && this._programUploaded) {
            this.startExecuting();
        }
    }

    public onPause() {
        this.pauseExecuting();
    }

    public onNext() {
        this.nextInstruction();
    }

    public onContinue() {
        this.continueExecuting();
    }

    public onStop() {
        this.stopExecuting();
    }

    private startExecuting() {
        console.log("Control Start: Start");
        this._socketClient.push("/emulator_in/monitor/control", {
            "sessionId": this._sessionId,
            "operation": "start"
        });
    }

    private pauseExecuting() {
        console.log("Control Start: Pause");
        this._socketClient.push("/emulator_in/monitor/control", {
            "sessionId": this._sessionId,
            "operation": "pause"
        });
    }

    private nextInstruction() {
        console.log("Control Start: Next");
        this._socketClient.push("/emulator_in/monitor/control", {
            "sessionId": this._sessionId,
            "operation": "next"
        });
    }

    private continueExecuting() {
        console.log("Control Start: Continue");
        this._socketClient.push("/emulator_in/monitor/control", {
            "sessionId": this._sessionId,
            "operation": "continue"
        });
    }

    private stopExecuting() {
        console.log("Control Start: Stop");
        this._socketClient.push("/emulator_in/monitor/control", {
            "sessionId": this._sessionId,
            "operation": "stop"
        });
    }

    private checkReady() {
        if (this.getLastStatus() != "Ready") {
            this._socketClient.push("/emulator_in/monitor/control", {
                "sessionId": this._sessionId,
                "operation": "check"
            });
        }
        else {
            clearInterval(this._checkTimer);
        }
    }

    constructor() {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
        this._checkTimer = setInterval(() => {
            this.checkReady()
        }, 1000);
    }

    configSocket():SocketConfig {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/monitor";
        config.subscribe("/emulator_response/monitor/control/status/{{client_id}}", (response:Message) => {
            this.onStatusResponse(response)
        });
        config.subscribe("/emulator_response/monitor/error/{{client_id}}", (response:Message) => {
            this.onError(response)
        });
        return config;
    }

    ngOnDestroy() {

    }
}

