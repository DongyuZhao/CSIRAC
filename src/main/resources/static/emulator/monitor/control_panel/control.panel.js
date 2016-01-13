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
            },
            function (guid_1_1) {
                guid_1 = guid_1_1;
            }],
        execute: function() {
            ControlPanel = (function () {
                function ControlPanel() {
                    var _this = this;
                    this._host = "localhost:8080/";
                    this._controlClient = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/control");
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    this._retryCount = 0;
                    this._allowStart = false;
                    this._allowPause = false;
                    this._allowContinue = false;
                    this._allowNext = false;
                    this._allowStop = false;
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
                ControlPanel.prototype.connect = function () {
                    var _this = this;
                    try {
                        if (this._controlClient != null && !this._controlClient.connected) {
                            this._controlClient.connect({}, function (frame) {
                                _this._controlClient.subscribe("/emulator_response/control/status/" + _this._sessionId, function (response) {
                                    console.log("Control Status");
                                    _this.onResponse(response);
                                });
                                _this._controlClient.subscribe("/emulator_response/control/error/" + _this._sessionId, function (response) {
                                    console.log("Control Error");
                                    _this.onError(response);
                                });
                            });
                        }
                        console.log("Control Client Connected");
                    }
                    catch (e) {
                        console.error(e);
                    }
                };
                ;
                ControlPanel.prototype.disconnect = function () {
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.disconnect();
                        console.log("Control Client Disconnect");
                    }
                };
                ControlPanel.prototype.startExecuting = function () {
                    console.log("Control Start: Start");
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "start"
                        }));
                    }
                    else {
                        if (this._retryCount < 3) {
                            this._retryCount = this._retryCount + 1;
                            this.connect();
                            this.startExecuting();
                        }
                        else {
                            this.errorList.push("No Connection");
                        }
                    }
                };
                ControlPanel.prototype.pauseExecuting = function () {
                    console.log("Control Start: Pause");
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "pause"
                        }));
                    }
                    else {
                        if (this._retryCount < 3) {
                            this._retryCount = this._retryCount + 1;
                            this.connect();
                            this.pauseExecuting();
                        }
                        else {
                            this.errorList.push("No Connection");
                        }
                    }
                };
                ControlPanel.prototype.nextInstruction = function () {
                    console.log("Control Start: Next");
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "next"
                        }));
                    }
                    else {
                        if (this._retryCount < 3) {
                            this._retryCount = this._retryCount + 1;
                            this.connect();
                            this.nextInstruction();
                        }
                        else {
                            this.errorList.push("No Connection");
                        }
                    }
                };
                ControlPanel.prototype.continueExecuting = function () {
                    console.log("Control Start: Continue");
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "continue"
                        }));
                    }
                    else {
                        if (this._retryCount < 3) {
                            this._retryCount = this._retryCount + 1;
                            this.connect();
                            this.continueExecuting();
                        }
                        else {
                            this.errorList.push("No Connection");
                        }
                    }
                };
                ControlPanel.prototype.stopExecuting = function () {
                    console.log("Control Start: Stop");
                    if (this._controlClient != null && this._controlClient.connected) {
                        this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "stop"
                        }));
                    }
                    else {
                        if (this._retryCount < 3) {
                            this._retryCount = this._retryCount + 1;
                            this.connect();
                            this.stopExecuting();
                        }
                        else {
                            this.errorList.push("No Connection");
                        }
                    }
                };
                ControlPanel.prototype.checkReady = function () {
                    if (this.getLastStatus() != "Ready") {
                        console.log("Control Start: Check");
                        if (this._controlClient != null && this._controlClient.connected) {
                            this._controlClient.send("/emulator_in/control", {}, JSON.stringify({
                                "sessionId": this._sessionId,
                                "operation": "check"
                            }));
                            console.log("Sent Check");
                        }
                        else {
                            if (this._retryCount < 3) {
                                this._retryCount = this._retryCount + 1;
                                this.connect();
                                this.checkReady();
                            }
                            else {
                                this.errorList.push("No Connection");
                            }
                        }
                    }
                    else {
                        clearInterval(this._checkTimer);
                    }
                };
                ControlPanel.prototype.ngOnDestroy = function () {
                    this.disconnect();
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