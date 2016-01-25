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
    var DebuggerClient;
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
            DebuggerClient = (function () {
                function DebuggerClient() {
                    this.pcRegister = 0;
                    this.opCode = 0;
                    this.clock = 1;
                    this.DataUpdateHistory = [];
                    this.ProgramUpdateHistory = [];
                    this.RegisterUpdateHistory = [];
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                    this._socketClient.connect();
                }
                DebuggerClient.prototype.onFrequencySubmit = function () {
                    this._socketClient.push("/emulator_in/debugger/input/clock", {
                        "sessionId": this._sessionId,
                        "frequency": this.clock
                    });
                };
                ;
                DebuggerClient.prototype.onPcRegisterSubmit = function () {
                    this._socketClient.push("/emulator_in/debugger/input/pc_reg", {
                        "sessionId": this._sessionId,
                        "address": this.pcRegister
                    });
                };
                ;
                DebuggerClient.prototype.onInstructionResponse = function (response) {
                    this.opCode = JSON.parse(response.body);
                };
                ;
                DebuggerClient.prototype.onDataMemoryResponse = function (response) {
                    //var result = JSON.parse(response.body);
                    this.DataUpdateHistory.push(JSON.parse(response.body));
                };
                ;
                DebuggerClient.prototype.onProgramMemoryResponse = function (response) {
                    this.ProgramUpdateHistory.push(JSON.parse(response.body));
                };
                ;
                DebuggerClient.prototype.onRegisterResponse = function (response) {
                    this.RegisterUpdateHistory.push(JSON.parse(response.body));
                };
                ;
                DebuggerClient.prototype.onPcRegResponse = function (response) {
                    this.pcRegister = JSON.parse(response.body);
                };
                DebuggerClient.prototype.onClockResponse = function (response) {
                    this.clock = JSON.parse(response.body);
                };
                // public onOutputResponse(response:Message)
                // {
                //     this.outputView = JSON.parse(response.body);
                // }
                DebuggerClient.prototype.onStatusResponse = function (response) {
                    if (this.statusList.length > 5) {
                        this.statusList = [];
                    }
                    this.statusList.push(JSON.parse(response.body));
                };
                DebuggerClient.prototype.onError = function (response) {
                    this.errorList.push(JSON.parse(response.body));
                };
                ;
                ;
                DebuggerClient.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_2.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/debugger";
                    config.subscribe("/emulator_response/debugger/opcode/{{client_id}}", function (response) {
                        _this.onInstructionResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/data_memory/{{client_id}}", function (response) {
                        _this.onDataMemoryResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/program_memory/{{client_id}}", function (response) {
                        _this.onProgramMemoryResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/register/{{client_id}}", function (response) {
                        _this.onRegisterResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/status/{{client_id}}", function (response) {
                        _this.onStatusResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/pc_reg/{{client_id}}", function (response) {
                        _this.onPcRegResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/clock/{{client_id}}", function (response) {
                        _this.onClockResponse(response);
                    });
                    config.subscribe("/emulator_response/debugger/error/{{client_id}}", function (response) {
                        _this.onError(response);
                    });
                    return config;
                };
                DebuggerClient.prototype.ngOnDestroy = function () {
                    this._socketClient.disconnect();
                };
                ;
                DebuggerClient = __decorate([
                    core_1.Component({
                        selector: "debugger",
                        templateUrl: "emulator/debugger/client.html",
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], DebuggerClient);
                return DebuggerClient;
            })();
            exports_1("DebuggerClient", DebuggerClient);
        }
    }
});
//# sourceMappingURL=client.js.map