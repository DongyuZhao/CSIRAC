import {Component} from "angular2/core";
import {HTTP_PROVIDERS} from "angular2/http";
import {SocketServices} from "../../../services/socket_services";
import {Message} from "stompjs";
import {OnDestroy} from "angular2/core";
import {Frame} from "stompjs";
import {Guid} from "../../../utils/guid"
import {Settings} from "./settings";

@Component({
    selector: 'setting-panel',
    templateUrl: 'emulator/monitor/setting_panel/setting_panel.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class SettingPanel implements OnDestroy
{
    _host = "localhost:8080/";

    private _sessionId = "";

    private _client = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/settings");

    public statusList:string[] = [];

    public errorList:string[] = [];

    private _setting:Settings = null;

    private _retryCount = 0;

    public onStatusResponse(response:Message)
    {
        if (this.statusList.length >= 5)
        {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    }

    public onError(response:Message)
    {
        if (this.errorList.length >= 5)
        {
            this.errorList = [];
        }
        this.errorList.push(JSON.parse(response.body));
    }

    public onSettingResponse(response:Message)
    {
        this._setting = JSON.parse(response.body);
    }

    public onSubmit()
    {
        if (this._client != null && this._client.connected)
        {
            this._client.send("/emulator_in/settings", {}, JSON.stringify(this._setting));
            console.log("Setting Upload Finished");
        }
        else
        {
            if (this._retryCount < 3)
            {
                this.connect();
                this._client.send("/emulator_in/settings", {}, JSON.stringify(this._setting));
            }
            else
            {
                this.errorList.push("No Connection");
            }
        }
    }

    private connect()
    {
        if (this._client != null && !this._client.connected)
        {
            try
            {
                this._client.connect({},
                        (frame:Frame) =>
                        {
                            this._client.subscribe("/emulator_response/settings/current/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Settings Response");
                                        this.onSettingResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/settings/status/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Settings Status");
                                        this.onStatusResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/settings/error/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Settings Error");
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

    private initSettings()
    {
        this._setting = new Settings(this._sessionId, 100);
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
        this.initSettings();
    };

    ngOnDestroy()
    {
        this.disconnect();
    };
}