System.register(["angular2/core", "angular2/http", "./settings", "../../../services/socket.services", "../../../services/session.services"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, settings_1, socket_services_1, session_services_1;
    var SettingPanel;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (settings_1_1) {
                settings_1 = settings_1_1;
            },
            function (socket_services_1_1) {
                socket_services_1 = socket_services_1_1;
            },
            function (session_services_1_1) {
                session_services_1 = session_services_1_1;
            }],
        execute: function() {
            SettingPanel = (function () {
                function SettingPanel() {
                    this._sessionId = "";
                    this.statusList = [];
                    this.errorList = [];
                    this._setting = null;
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                    this._socketClient.connect();
                    this.initSettings();
                }
                SettingPanel.prototype.onStatusResponse = function (response) {
                    if (this.statusList.length >= 5) {
                        this.statusList = [];
                    }
                    this.statusList.push(JSON.parse(response.body));
                };
                SettingPanel.prototype.onError = function (response) {
                    if (this.errorList.length >= 5) {
                        this.errorList = [];
                    }
                    this.errorList.push(JSON.parse(response.body));
                };
                SettingPanel.prototype.onSettingResponse = function (response) {
                    this._setting = JSON.parse(response.body);
                };
                SettingPanel.prototype.onSubmit = function () {
                    this._socketClient.push("/emulator_in/settings", this._setting);
                };
                SettingPanel.prototype.initSettings = function () {
                    this._setting = new settings_1.Settings(this._sessionId, 100);
                };
                ;
                ;
                SettingPanel.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_1.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/settings";
                    config.subscribe("/emulator_response/settings/current/{{client_id}}", function (response) {
                        _this.onSettingResponse(response);
                    });
                    config.subscribe("/emulator_response/settings/status/{{client_id}}", function (response) {
                        _this.onStatusResponse(response);
                    });
                    config.subscribe("/emulator_response/settings/error/{{client_id}}", function (response) {
                        _this.onError(response);
                    });
                    return config;
                };
                SettingPanel.prototype.ngOnDestroy = function () {
                    this._socketClient.disconnect();
                };
                ;
                SettingPanel = __decorate([
                    core_1.Component({
                        selector: 'setting-panel',
                        templateUrl: 'emulator/monitor/setting_panel/setting_panel.html',
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], SettingPanel);
                return SettingPanel;
            })();
            exports_1("SettingPanel", SettingPanel);
        }
    }
});
//# sourceMappingURL=setting.panel.js.map