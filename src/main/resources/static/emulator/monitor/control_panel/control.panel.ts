import {Component} from "angular2/core";
import {HTTP_PROVIDERS} from "angular2/http";
import {Frame} from "stompjs";
import {Message} from "stompjs";
import {OnDestroy} from "angular2/core";
import {Guid} from "../../../utils/guid"
import {MonitorComponent} from "../monitor.component";
import {SocketConfig} from "../../../services/socket.services";
import {SocketClient} from "../../../services/socket.services";
import {SessionServices} from "../../../services/session.services";
import {SessionEnabledClient} from "../../../services/session.services";

@Component({
    selector: 'control-panel',
    templateUrl: 'emulator/monitor/control_panel/control_panel.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class ControlPanel implements OnDestroy, SessionEnabledClient
{
    private _socketClient:SocketClient;

    public statusList:string[] = [];

    public errorList:string[] = [];

    public _sessionId = "";

    private _allowStart = false;

    private _allowPause = false;

    private _allowContinue = false;

    private _allowNext = false;

    private _allowStop = false;

    private _checkTimer;

    private getLastError()
    {
        return this.errorList[this.errorList.length - 1];
    }

    private getLastStatus():string
    {
        return this.statusList[this.statusList.length - 1];
    }

    private onResponse(response:Message)
    {
        this.statusList.push(JSON.parse(response.body));
        switch (this.getLastStatus())
        {
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
            default:
                this._allowContinue = false;
                this._allowNext = false;
                this._allowPause = false;
                this._allowStart = false;
                this._allowStop = false;
                break;
        }

    }

    private onError(response:Message)
    {
        if (this.errorList.length >= 10)
        {
            this.errorList = [];
        }
        this.errorList = JSON.parse(response.body);
    }

    public onStart()
    {
        this.startExecuting();
    }

    public onPause()
    {
        this.pauseExecuting();
    }

    public onNext()
    {
        this.nextInstruction();
    }

    public onContinue()
    {
        this.continueExecuting();
    }

    public onStop()
    {
        this.stopExecuting();
    }

    private startExecuting()
    {
        console.log("Control Start: Start");
        this._socketClient.push("/emulator_in/control",{
                    "sessionId": this._sessionId,
                    "operation": "start"
                });
    }

    private pauseExecuting()
    {
        console.log("Control Start: Pause");
        this._socketClient.push("/emulator_in/control",{
            "sessionId": this._sessionId,
            "operation": "pause"
        });
    }

    private nextInstruction()
    {
        console.log("Control Start: Next");
        this._socketClient.push("/emulator_in/control",{
            "sessionId": this._sessionId,
            "operation": "next"
        });
    }

    private continueExecuting()
    {
        console.log("Control Start: Continue");
        this._socketClient.push("/emulator_in/control",{
            "sessionId": this._sessionId,
            "operation": "continue"
        });
    }

    private stopExecuting()
    {
        console.log("Control Start: Stop");
        this._socketClient.push("/emulator_in/control",{
            "sessionId": this._sessionId,
            "operation": "stop"
        });
    }

    private checkReady()
    {
        if (this.getLastStatus() != "Ready")
        {
            this._socketClient.push("/emulator_in/control",{
                "sessionId": this._sessionId,
                "operation": "check"
            });
        }
        else
        {
            clearInterval(this._checkTimer);
        }
    }

    constructor()
    {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
        this._checkTimer = setInterval(() =>
        {
            this.checkReady()
        }, 1000);
    }

    configSocket():SocketConfig
    {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/control";
        config.subscribe("/emulator_response/control/status/{{client_id}}", (response:Message) =>
        {
            this.onResponse(response)
        });
        config.subscribe("/emulator_response/control/error/{{client_id}}", (response:Message) =>
        {
            this.onError(response)
        });
        return config;
    }


    ngOnDestroy()
    {
        this._socketClient.disconnect();
    };
}