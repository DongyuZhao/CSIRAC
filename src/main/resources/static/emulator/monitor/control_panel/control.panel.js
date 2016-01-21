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
    var ControlPanel;
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
            ControlPanel = (function () {
                function ControlPanel() {
                    var _this = this;
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    this._allowStart = false;
                    this._allowPause = false;
                    this._allowContinue = false;
                    this._allowNext = false;
                    this._allowStop = false;
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_2.SocketClient(this.configSocket());
                    this._socketClient.connect();
                    this._checkTimer = setInterval(function () {
                        _this.checkReady();
                    }, 1000);
                }
                ControlPanel.prototype.getLastError = function () {
                    return this.errorList[this.errorList.length - 1];
                };
                ControlPanel.prototype.getLastStatus = function () {
                    return this.statusList[this.statusList.length - 1];
                };
                ControlPanel.prototype.onResponse = function (response) {
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
                        default:
                            this._allowContinue = false;
                            this._allowNext = false;
                            this._allowPause = false;
                            this._allowStart = false;
                            this._allowStop = false;
                            break;
                    }
                };
                ControlPanel.prototype.onError = function (response) {
                    if (this.errorList.length >= 10) {
                        this.errorList = [];
                    }
                    this.errorList = JSON.parse(response.body);
                };
                ControlPanel.prototype.onStart = function () {
                    this.startExecuting();
                };
                ControlPanel.prototype.onPause = function () {
                    this.pauseExecuting();
                };
                ControlPanel.prototype.onNext = function () {
                    this.nextInstruction();
                };
                ControlPanel.prototype.onContinue = function () {
                    this.continueExecuting();
                };
                ControlPanel.prototype.onStop = function () {
                    this.stopExecuting();
                };
                ControlPanel.prototype.startExecuting = function () {
                    console.log("Control Start: Start");
                    this._socketClient.push("/emulator_in/control", {
                        "sessionId": this._sessionId,
                        "operation": "start"
                    });
                };
                ControlPanel.prototype.pauseExecuting = function () {
                    console.log("Control Start: Pause");
                    this._socketClient.push("/emulator_in/control", {
                        "sessionId": this._sessionId,
                        "operation": "pause"
                    });
                };
                ControlPanel.prototype.nextInstruction = function () {
                    console.log("Control Start: Next");
                    this._socketClient.push("/emulator_in/control", {
                        "sessionId": this._sessionId,
                        "operation": "next"
                    });
                };
                ControlPanel.prototype.continueExecuting = function () {
                    console.log("Control Start: Continue");
                    this._socketClient.push("/emulator_in/control", {
                        "sessionId": this._sessionId,
                        "operation": "continue"
                    });
                };
                ControlPanel.prototype.stopExecuting = function () {
                    console.log("Control Start: Stop");
                    this._socketClient.push("/emulator_in/control", {
                        "sessionId": this._sessionId,
                        "operation": "stop"
                    });
                };
                ControlPanel.prototype.checkReady = function () {
                    if (this.getLastStatus() != "Ready") {
                        this._socketClient.push("/emulator_in/control", {
                            "sessionId": this._sessionId,
                            "operation": "check"
                        });
                    }
                    else {
                        clearInterval(this._checkTimer);
                    }
                };
                ControlPanel.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_1.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/control";
                    config.subscribe("/emulator_response/control/status/{{client_id}}", function (response) {
                        _this.onResponse(response);
                    });
                    config.subscribe("/emulator_response/control/error/{{client_id}}", function (response) {
                        _this.onError(response);
                    });
                    return config;
                };
                ControlPanel.prototype.ngOnDestroy = function () {
                    this._socketClient.disconnect();
                };
                ;
                ControlPanel = __decorate([
                    core_1.Component({
                        selector: 'control-panel',
                        templateUrl: 'emulator/monitor/control_panel/control_panel.html',
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], ControlPanel);
                return ControlPanel;
            })();
            exports_1("ControlPanel", ControlPanel);
        }
    }
});
//# sourceMappingURL=control.panel.js.map