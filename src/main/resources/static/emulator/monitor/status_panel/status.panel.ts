import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketServices} from "../../../services/socket_services";
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../../utils/guid"
import {MonitorComponent} from "../monitor.component";

@Component({
    selector: "status-panel",
    templateUrl: "emulator/monitor/status_panel/status_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class StatusPanel implements OnDestroy
{

    _host = "localhost:8080/";

    private _Client = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");

    public statusList:string[] = [];

    public errorList:string[] = [];

    private _sessionId = "";

    private _Timer = null;

    private _Count = 0;

    public onResponse(response:Message)
    {
        if (this.statusList.length >= 5)
        {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    };

    public onError(response:Message)
    {
        if (this.errorList.length >= 5)
        {
            this.errorList = [];
        }
        this.errorList.push(JSON.parse(response.body));
    };

    private connect()
    {
        if (this._Client != null && !this._Client.connected)
        {
            try
            {
                this._Client.connect({},
                        (frame:Frame) =>
                        {
                            this._Client.subscribe("/emulator_response/hand_shake/" + this._sessionId,
                                    (response:Message)=>
                                    {
                                        console.log("Hand Shake Response");
                                        this.onResponse(response);
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

    private disconnect()
    {
        clearInterval(this._Timer);
        if (this._Client != null && this._Client.connected)
        {
            this._Client.send("/emulator_in/hand_shake", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "bye"
            }));
            this._Client.disconnect();
            console.log(" Client Disconnect");
        }
    };

    private keepSessionActive()
    {
        console.log("Hand Shake Start");
        if (this._Client != null && this._Client.connected)
        {
            this._Count = this._Count + 1;
            this._Client.send("/emulator_in/hand_shake", {}, JSON.stringify({
                "sessionId": this._sessionId,
                "operation": "hello"
            }));
            console.log(" Active:" + this._Count);
        }
        else
        {
            console.log(this._Count);
            clearInterval(this._Timer);
            this._Count = 0;
            this._Client = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");
            this.connect();
            this._Timer = setInterval(() =>
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
        this.connect();
        console.log("Connect Complete");
        setTimeout(() =>
        {
            this.keepSessionActive()
        }, 1000);
        this._Timer = setInterval(() =>
        {
            this.keepSessionActive()
        }, 5000);
    };

    ngOnDestroy()
    {
        this.disconnect();
    };
}
