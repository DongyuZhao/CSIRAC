System.register(["angular2/core", "angular2/http", "../../services/socket.services", "../../services/session.services"], function(exports_1) {
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
    var MonitorClient;
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
            MonitorClient = (function () {
                function MonitorClient() {
                    var _this = this;
                    this._sessionId = "";
                    this.statusList = [];
                    this.errorList = [];
                    this.program = "0 0 M A\n0 0 M B\n0 0 M C\n0 1 A M\n0 2 B M\n0 3 C M";
                    this.structured_program = [];
                    this.data = "1 1 1 1";
                    this.structured_data = [];
                    this._allowStart = false;
                    this._allowPause = false;
                    this._allowContinue = false;
                    this._allowNext = false;
                    this._allowStop = false;
                    this._dataUploaded = false;
                    this._programUploaded = false;
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                    this._socketClient.connect();
                    this._checkTimer = setInterval(function () {
                        _this.checkReady();
                    }, 1000);
                }
                MonitorClient.prototype.getLastError = function () {
                    return this.errorList[this.errorList.length - 1];
                };
                MonitorClient.prototype.getLastStatus = function () {
                    return this.statusList[this.statusList.length - 1];
                };
                MonitorClient.prototype.onStatusResponse = function (response) {
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
                        case "Data Loaded":
                            this._dataUploaded = true;
                            break;
                        case "Program Loaded":
                            this._programUploaded = true;
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
                MonitorClient.prototype.onError = function (response) {
                    if (this.errorList.length >= 10) {
                        this.errorList = [];
                    }
                    this.errorList = JSON.parse(response.body);
                };
                MonitorClient.prototype.onDataSubmit = function () {
                    console.log("Upload Data");
                    if (this.data != null && this.data != "") {
                        this.structured_data = this.data.split("\n");
                    }
                    if (this.structured_data != null && this.structured_data.length != 0) {
                        this._socketClient.push("/emulator_in/monitor/input/data", {
                            "sessionId": this._sessionId,
                            "data": this.structured_data
                        });
                    }
                    else {
                        console.error("Data Null");
                    }
                };
                MonitorClient.prototype.onProgramSubmit = function () {
                    console.log("Upload Program");
                    if (this.program != null && this.program != "") {
                        this.structured_program = this.program.split("\n");
                    }
                    if (this.structured_program != null && this.structured_program.length != 0) {
                        this._socketClient.push("/emulator_in/monitor/input/program", {
                            "sessionId": this._sessionId,
                            "program": this.structured_program
                        });
                    }
                    else {
                        console.error("Program Null");
                    }
                };
                MonitorClient.prototype.onStart = function () {
                    if (this._dataUploaded && this._programUploaded) {
                        this.startExecuting();
                    }
                };
                MonitorClient.prototype.onPause = function () {
                    this.pauseExecuting();
                };
                MonitorClient.prototype.onNext = function () {
                    this.nextInstruction();
                };
                MonitorClient.prototype.onContinue = function () {
                    this.continueExecuting();
                };
                MonitorClient.prototype.onStop = function () {
                    this.stopExecuting();
                };
                MonitorClient.prototype.startExecuting = function () {
                    console.log("Control Start: Start");
                    this._socketClient.push("/emulator_in/monitor/control", {
                        "sessionId": this._sessionId,
                        "operation": "start"
                    });
                };
                MonitorClient.prototype.pauseExecuting = function () {
                    console.log("Control Start: Pause");
                    this._socketClient.push("/emulator_in/monitor/control", {
                        "sessionId": this._sessionId,
                        "operation": "pause"
                    });
                };
                MonitorClient.prototype.nextInstruction = function () {
                    console.log("Control Start: Next");
                    this._socketClient.push("/emulator_in/monitor/control", {
                        "sessionId": this._sessionId,
                        "operation": "next"
                    });
                };
                MonitorClient.prototype.continueExecuting = function () {
                    console.log("Control Start: Continue");
                    this._socketClient.push("/emulator_in/monitor/control", {
                        "sessionId": this._sessionId,
                        "operation": "continue"
                    });
                };
                MonitorClient.prototype.stopExecuting = function () {
                    console.log("Control Start: Stop");
                    this._socketClient.push("/emulator_in/monitor/control", {
                        "sessionId": this._sessionId,
                        "operation": "stop"
                    });
                };
                MonitorClient.prototype.checkReady = function () {
                    if (this.getLastStatus() != "Ready") {
                        this._socketClient.push("/emulator_in/monitor/control", {
                            "sessionId": this._sessionId,
                            "operation": "check"
                        });
                    }
                    else {
                        clearInterval(this._checkTimer);
                    }
                };
                MonitorClient.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_2.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/monitor";
                    config.subscribe("/emulator_response/monitor/control/status/{{client_id}}", function (response) {
                        _this.onStatusResponse(response);
                    });
                    config.subscribe("/emulator_response/monitor/error/{{client_id}}", function (response) {
                        _this.onError(response);
                    });
                    return config;
                };
                MonitorClient.prototype.ngOnDestroy = function () {
                };
                MonitorClient = __decorate([
                    core_1.Component({
                        selector: "monitor",
                        templateUrl: "emulator/monitor/client.html",
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], MonitorClient);
                return MonitorClient;
            })();
            exports_1("MonitorClient", MonitorClient);
        }
    }
});
//# sourceMappingURL=client.js.map