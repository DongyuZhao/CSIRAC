System.register(["angular2/core", "../../../services/socket_services", "angular2/http", "../../../utils/guid"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, socket_services_1, http_1, guid_1;
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
            function (guid_1_1) {
                guid_1 = guid_1_1;
            }],
        execute: function() {
            IoPanel = (function () {
                function IoPanel() {
                    this._host = "localhost:8080/";
                    this._ioClient = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/io");
                    this._handShakeClient = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");
                    this.program = "";
                    this.structured_program = [];
                    this.instructionView = [];
                    this.memoryView = [];
                    this.registerView = [];
                    this.statue = "";
                    this.error = "";
                    this._sessionId = "";
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
                    this.connectHandShakeSocket();
                    this.connectIoSocket();
                }
                IoPanel.prototype.onSubmit = function () {
                    if (this.program != null && this.program != "") {
                        this.structured_program = this.program.split("\n");
                    }
                    if (this.structured_program != null && this.structured_program.length != 0) {
                        if (this._ioClient != null && this._ioClient.connected) {
                            this._ioClient.send("/emulator_in/io", {}, JSON.stringify({
                                "sessionId": this._sessionId,
                                "program": this.structured_program
                            }));
                        }
                        else {
                            console.error("IO Client Null or UnConnected");
                        }
                    }
                    else {
                        console.error("Program Null");
                    }
                };
                ;
                IoPanel.prototype.onInstructionResponse = function (response) {
                    this.instructionView = JSON.parse(response.body).content;
                };
                ;
                IoPanel.prototype.onMemoryResponse = function (response) {
                    this.memoryView = JSON.parse(response.body).content;
                };
                ;
                IoPanel.prototype.onRegisterResponse = function (response) {
                    this.registerView = JSON.parse(response.body).content;
                };
                ;
                IoPanel.prototype.onHandShakeResponse = function (response) {
                    this.statue = JSON.parse(response.body).content;
                };
                ;
                IoPanel.prototype.onError = function (response) {
                    this.error = JSON.parse(response.body).content;
                };
                ;
                IoPanel.prototype.connectIoSocket = function () {
                    var _this = this;
                    if (this._ioClient != null && !this._ioClient.connected) {
                        try {
                            this._ioClient.connect({}, function (frame) {
                                _this._ioClient.subscribe("/emulator_response/instruction/" + _this._sessionId, function (response) {
                                    console.log("Instruction Response");
                                    _this.onInstructionResponse(response);
                                });
                                _this._ioClient.subscribe("/emulator_response/memory/" + _this._sessionId, function (response) {
                                    console.log("Memory Response");
                                    _this.onMemoryResponse(response);
                                });
                                _this._ioClient.subscribe("/emulator_response/register/" + _this._sessionId, function (response) {
                                    console.log("Register Response");
                                    _this.onRegisterResponse(response);
                                });
                                _this._ioClient.subscribe("/emulator_response/error/" + _this._sessionId, function (response) {
                                    console.log("IO Error Response");
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
                IoPanel.prototype.connectHandShakeSocket = function () {
                    var _this = this;
                    if (this._handShakeClient != null && !this._handShakeClient.connected) {
                        try {
                            this._handShakeClient.connect({}, function (frame) {
                                _this._handShakeClient.subscribe("/emulator_response/hand_shake/" + _this._sessionId, function (response) {
                                    console.log("Hand Shake Response");
                                    _this.onHandShakeResponse(response);
                                });
                            });
                            console.log("HandShake Connected");
                        }
                        catch (e) {
                            console.error(e);
                        }
                    }
                };
                ;
                IoPanel.prototype.disconnectHandShakeSocket = function () {
                    if (this._handShakeClient != null && this._handShakeClient.connected) {
                        this._handShakeClient.send("/emulator_in/hand_shake", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "bye"
                        }));
                        this._handShakeClient.disconnect();
                        console.log("HandShake Client Disconnect");
                    }
                };
                ;
                IoPanel.prototype.disconnectIoSocket = function () {
                    if (this._ioClient != null && this._ioClient.connected) {
                        this._ioClient.disconnect();
                        console.log("IO Client Disconnect");
                    }
                };
                ;
                IoPanel.prototype.keepAlive = function () {
                    if (this._handShakeClient != null && this._handShakeClient.connected) {
                        this._handShakeClient.send("/emulator_in/hand_shake", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "hello"
                        }));
                        console.log("HandShake Alive");
                    }
                };
                ;
                IoPanel.prototype.ngOnDestroy = function () {
                    this.disconnectHandShakeSocket();
                    this.disconnectIoSocket();
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