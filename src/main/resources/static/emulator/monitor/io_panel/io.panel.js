System.register(["angular2/core", "../../../services/socket.services", "angular2/http", "../../../services/session.services"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, socket_services_1, http_1, session_services_1;
    var IoPanel;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (socket_services_1_1) {
                socket_services_1 = socket_services_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (session_services_1_1) {
                session_services_1 = session_services_1_1;
            }],
        execute: function() {
            IoPanel = (function () {
                function IoPanel() {
                    this.program = "";
                    this.structured_program = [];
                    this.frequencyView = "";
                    this.instructionView = "";
                    this.pcRegView = "";
                    this.memoryView = [];
                    this.registerView = [];
                    this.outputView = [];
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    session_services_1.SessionServices.ensureSessionId(this);
                    this._socketClient = new socket_services_1.SocketClient(this.configSocket());
                    this._socketClient.connect();
                }
                IoPanel.prototype.onSubmit = function () {
                    console.log("Upload Program");
                    if (this.program != null && this.program != "") {
                        this.structured_program = this.program.split("\n");
                    }
                    if (this.structured_program != null && this.structured_program.length != 0) {
                        this._socketClient.push("/emulator_in/io", {
                            "sessionId": this._sessionId,
                            "program": this.structured_program
                        });
                    }
                    else {
                        console.error("Program Null");
                    }
                };
                ;
                IoPanel.prototype.onInstructionResponse = function (response) {
                    this.instructionView = JSON.parse(response.body);
                };
                ;
                IoPanel.prototype.onMemoryResponse = function (response) {
                    this.memoryView = JSON.parse(response.body);
                };
                ;
                IoPanel.prototype.onRegisterResponse = function (response) {
                    this.registerView = JSON.parse(response.body);
                };
                ;
                IoPanel.prototype.onPcRegResponse = function (response) {
                    this.pcRegView = JSON.parse(response.body);
                };
                IoPanel.prototype.onOutputResponse = function (response) {
                    this.outputView = JSON.parse(response.body);
                };
                IoPanel.prototype.onStatusResponse = function (response) {
                    if (this.statusList.length > 5) {
                        this.statusList = [];
                    }
                    this.statusList.push(JSON.parse(response.body));
                };
                IoPanel.prototype.onError = function (response) {
                    this.errorList.push(JSON.parse(response.body));
                };
                ;
                ;
                IoPanel.prototype.configSocket = function () {
                    var _this = this;
                    var config = new socket_services_1.SocketConfig();
                    config.clientId = this._sessionId;
                    config.connectUrl = "/emulator_in/io";
                    config.subscribe("/emulator_response/io/instruction/{{client_id}}", function (response) { _this.onInstructionResponse(response); });
                    config.subscribe("/emulator_response/io/memory/{{client_id}}", function (response) { _this.onMemoryResponse(response); });
                    config.subscribe("/emulator_response/io/register/{{client_id}}", function (response) { _this.onRegisterResponse(response); });
                    config.subscribe("/emulator_response/io/status/{{client_id}}", function (response) { _this.onStatusResponse(response); });
                    config.subscribe("/emulator_response/io/pc_reg/{{client_id}}", function (response) { _this.onPcRegResponse(response); });
                    config.subscribe("/emulator_response/io/output/{{client_id}}", function (response) { _this.onOutputResponse(response); });
                    config.subscribe("/emulator_response/io/error/{{client_id}}", function (response) { _this.onError(response); });
                    return config;
                };
                IoPanel.prototype.ngOnDestroy = function () {
                    this._socketClient.disconnect();
                };
                ;
                IoPanel = __decorate([
                    core_1.Component({
                        selector: "io-panel",
                        templateUrl: "emulator/monitor/io_panel/io_panel.html",
                        viewProviders: [http_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], IoPanel);
                return IoPanel;
            })();
            exports_1("IoPanel", IoPanel);
        }
    }
});
//# sourceMappingURL=io.panel.js.map