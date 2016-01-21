import {Component} from "angular2/core";
import {HTTP_PROVIDERS} from "angular2/http";
import {Message} from "stompjs";
import {OnDestroy} from "angular2/core";
import {Frame} from "stompjs";
import {Guid} from "../../../utils/guid"
import {Settings} from "./settings";
import {SocketConfig, SocketClient} from "../../../services/socket.services";
import {SessionServices} from "../../../services/session.services";
import {SessionEnabledClient} from "../../../services/session.services";

@Component({
    selector: 'setting-panel',
    templateUrl: 'emulator/monitor/setting_panel/setting_panel.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class SettingPanel implements OnDestroy, SessionEnabledClient
{

    public _sessionId = "";

    public statusList:string[] = [];

    public errorList:string[] = [];

    private _setting:Settings = null;

    private _socketClient:SocketClient;

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
        this._socketClient.push("/emulator_in/settings", this._setting);
    }

    private initSettings()
    {
        this._setting = new Settings(this._sessionId, 100);
    };

    constructor()
    {
        SessionServices.ensureSessionId(this);
        this._socketClient = new SocketClient(this.configSocket());
        this._socketClient.connect();
        this.initSettings();
    };

    configSocket():SocketConfig
    {
        var config = new SocketConfig();
        config.clientId = this._sessionId;
        config.connectUrl = "/emulator_in/settings";
        config.subscribe("/emulator_response/settings/current/{{client_id}}", (response:Message) =>
        {
            this.onSettingResponse(response)
        });
        config.subscribe("/emulator_response/settings/status/{{client_id}}", (response:Message) =>
        {
            this.onStatusResponse(response)
        });
        config.subscribe("/emulator_response/settings/error/{{client_id}}", (response:Message) =>
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
