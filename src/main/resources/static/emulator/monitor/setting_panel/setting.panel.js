System.register(["angular2/core", "angular2/http", "../../../services/socket_services", "../../../utils/guid"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, socket_services_1, guid_1;
    var SettingPanel;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (socket_services_1_1) {
                socket_services_1 = socket_services_1_1;
            },
            function (guid_1_1) {
                guid_1 = guid_1_1;
            }],
        execute: function() {
            SettingPanel = (function () {
                function SettingPanel() {
                    this._host = "localhost:8080/";
                    this._sessionId = "";
                    this._client = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/settings");
                    this.frequency = 100;
                    this.statusList = [];
                    this.errorList = [];
                    var node = document.getElementById("session_id");
                    if (node == null) {
                        this._sessionId = guid_1.Guid.newGuid();
                    }
                    else {
                        this._sessionId = node.innerText;
                        if (node.innerText == null || node.innerText == "") {
                            this._sessionId = guid_1.Guid.newGuid();
                            node.innerText = this._sessionId;
                        }
                    }
                    this.connect();
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
                    var result = JSON.parse(response.body);
                    this.frequency = result["frequency"];
                };
                SettingPanel.prototype.connect = function () {
                    var _this = this;
                    if (this._client != null && !this._client.connected) {
                        try {
                            this._client.connect({}, function (frame) {
                                _this._client.subscribe("/emulator_response/settings/current/" + _this._sessionId, function (response) {
                                    console.log("Settings Response");
                                    _this.onSettingResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/settings/status/" + _this._sessionId, function (response) {
                                    console.log("Settings Status");
                                    _this.onStatusResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/settings/error/" + _this._sessionId, function (response) {
                                    console.log("Settings Error");
                                    _this.onError(response);
                                });
                            });
                            console.log("IO Client Connected");
                        }
                        catch (e) {
                            console.error(e);
                        }
                    }
                };
                ;
                SettingPanel.prototype.disconnect = function () {
                    if (this._client != null && this._client.connected) {
                        this._client.disconnect();
                        console.log("IO Client Disconnect");
                    }
                };
                ;
                ;
                SettingPanel.prototype.ngOnDestroy = function () {
                    this.disconnect();
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