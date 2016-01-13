import {Component} from "angular2/core";
import {HTTP_PROVIDERS} from "angular2/http";
import {SocketServices} from "../../../services/socket_services";
import {Frame} from "stompjs";
import {Message} from "stompjs";
import {OnDestroy} from "angular2/core";
import {Guid} from "../../../utils/guid"
import {MonitorComponent} from "../monitor.component";

@Component({
    selector: 'control-panel',
    templateUrl: 'emulator/monitor/control_panel/control_panel.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class ControlPanel implements OnDestroy
{
    _host = "localhost:8080/";

    private _controlClient = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/control");

    private statusList = [];

    public errorList = [];

    private _sessionId = "";

    private _retryCount = 0;

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


    private connect()
    {
        try
        {
            if (this._controlClient != null && !this._controlClient.connected)
            {
                this._controlClient.connect({},
                        (frame:Frame) =>
                        {
                            this._controlClient.subscribe("/emulator_response/control/status/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Control Response");
                                        this.onResponse(response);
                                    });
                            this._controlClient.subscribe("/emulator_response/control/error/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Control Error");
                                        this.onError(response);
                                    }
                            );
                        });
            }
            console.log("Control Client Connected");
        }
        catch (e)
        {
            console.error(e)
        }
    };

    private disconnect()
    {
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.disconnect();
            console.log("Control Client Disconnect");
        }
    }

    private startExecuting()
    {
        console.log("Control Start: Start");
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "start"
            }))
        }
        else
        {
            if (this._retryCount < 3)
            {
                this._retryCount = this._retryCount + 1;
                this.connect();
                this.startExecuting();
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private pauseExecuting()
    {
        console.log("Control Start: Pause");
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "pause"
            }))
        }
        else
        {
            if (this._retryCount < 3)
            {
                this._retryCount = this._retryCount + 1;
                this.connect();
                this.pauseExecuting();
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private nextInstruction()
    {
        console.log("Control Start: Next");
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "next"
            }))
        }
        else
        {
            if (this._retryCount < 3)
            {
                this._retryCount = this._retryCount + 1;
                this.connect();
                this.nextInstruction();
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private continueExecuting()
    {
        console.log("Control Start: Continue");
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "continue"
            }))
        }
        else
        {
            if (this._retryCount < 3)
            {
                this._retryCount = this._retryCount + 1;
                this.connect();
                this.continueExecuting();
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private stopExecuting()
    {
        console.log("Control Start: Stop");
        if (this._controlClient != null && this._controlClient.connected)
        {
            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "stop"
            }))
        }
        else
        {
            if (this._retryCount < 3)
            {
                this._retryCount = this._retryCount + 1;
                this.connect();
                this.stopExecuting();
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private checkReady()
    {
        if (this.getLastStatus() != "Ready")
        {
            console.log("Control Start: Check");
            if (this._controlClient != null && this._controlClient.connected)
            {
                this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                    "sessionId": this._sessionId,
                    "operation": "check"
                }))
                console.log("Sent Check");
            }
            else
            {
                if (this._retryCount < 3)
                {
                    this._retryCount = this._retryCount + 1;
                    this.connect();
                    this.checkReady();
                }
                else
                {
                    this.errorList.push("No Connection");
                }
            }
        }
        else
        {
            clearInterval(this._checkTimer);
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
        this.connect();
        this._checkTimer = setInterval(() =>
        {
            this.checkReady()
        }, 1000);
    }

    ngOnDestroy()
    {
        this.disconnect();
    };
}