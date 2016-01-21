System.register(["angular2/core", "angular2/http", "../../../services/socket.services", "../../../services/session.services"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, socket_services_1, socket_services_2, session_services_1;
    var StatusPanel;
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
                socket_services_2 = socket_services_1_1;
            },
            function (session_services_1_1) {
                session_services_1 = session_services_1_1;
            }],
        execute: function() {
            StatusPanel = (function () {
                function StatusPanel() {
                    var _this = this;
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    this._Timer = null;
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                    this._socketClient.connect();
                    setTimeout(function () {
                        _this.keepSessionActive();
                    }, 1000);
                    this._Timer = setInterval(function () {
                        _this.keepSessionActive();
                    }, 5000);
                }
                StatusPanel.prototype.onResponse = function (response) {
                    if (this.statusList.length >= 5) {
                        this.statusList = [];
                    }
                    this.statusList.push(JSON.parse(response.body));
                };
                ;
                StatusPanel.prototype.onError = function (response) {
                    if (this.errorList.length >= 5) {
                        this.errorList = [];
                    }
                    this.errorList.push(JSON.parse(response.body));
                };
                ;
                StatusPanel.prototype.keepSessionActive = function () {
                    var _this = this;
                    try {
                        this._socketClient.push("/emulator_in/hand_shake", {
                            "sessionId": this._sessionId,
                            "operation": "hello"
                        });
                    }
                    catch (e) {
                        clearInterval(this._Timer);
                        this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                        this._socketClient.connect();
                        this._Timer = setInterval(function () {
                            _this.keepSessionActive();
                        }, 5000);
                    }
                };
                ;
                StatusPanel.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_2.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/hand_shake";
                    config.subscribe("/emulator_response/hand_shake/{{client_id}}", function (response) {
                        _this.onResponse(response);
                    });
                    console.log(config.subscribeMap);
                    return config;
                };
                StatusPanel.prototype.ngOnDestroy = function () {
                    this._socketClient.disconnect();
                };
                ;
                StatusPanel = __decorate([
                    core_1.Component({
                        selector: "status-panel",
                        templateUrl: "emulator/monitor/status_panel/status_panel.html",
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], StatusPanel);
                return StatusPanel;
            })();
            exports_1("StatusPanel", StatusPanel);
        }
    }
});
//# sourceMappingURL=status.panel.js.map