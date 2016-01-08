System.register(["../../utils/guid", 'stompjs'], function(exports_1) {
    var guid_1, stompjs_1;
    var ConnectionAgent;
    return {
        setters:[
            function (guid_1_1) {
                guid_1 = guid_1_1;
            },
            function (stompjs_1_1) {
                stompjs_1 = stompjs_1_1;
            }],
        execute: function() {
            ConnectionAgent = (function () {
                function ConnectionAgent(onHandShakeResponse, onMemoryUpdate, onRegisterUpdate, onCurrentInstructionUpdate, onError) {
                    this.onHandShakeResponse = onHandShakeResponse;
                    this.onMemoryUpdate = onMemoryUpdate;
                    this.onRegisterUpdate = onRegisterUpdate;
                    this.onCurrentInstructionUpdate = onCurrentInstructionUpdate;
                    this.onError = onError;
                    this.sessionId = guid_1.Guid.newGuid();
                    this._connectionSockets = [];
                    this._handShakeClient = null;
                    this._ioClient = null;
                    this._controlClient = null;
                    this.handShakeConnectUrl = "emulator_in/hand_shake";
                    this.handShakeSubscribeUrl = "emulator_response/hand_shake";
                    this.ioConnectUrl = "emulator_in/io";
                    this.ioSubscribeUrl = "emulator_response/io";
                    this.controlConnectUrl = "emulator_in/control";
                    this.controlSubscribeUrl = "emulator_response/control";
                    this.connect();
                }
                ConnectionAgent.getSafeString = function (src) {
                    var result = "";
                    if (src != null) {
                        result = src;
                    }
                    return result;
                };
                ConnectionAgent.buildSubscribeUrl = function (baseUrl, topic, sessionId) {
                    var safeBaseUrl = ConnectionAgent.getSafeString(baseUrl);
                    var safeTopic = ConnectionAgent.getSafeString(topic);
                    var safeSessionId = ConnectionAgent.getSafeString(sessionId);
                    if (safeBaseUrl.length != 0 && safeBaseUrl.charAt(safeBaseUrl.length - 1) != '/') {
                        safeBaseUrl += '/';
                    }
                    if (safeTopic.length != 0 && safeTopic.charAt(safeBaseUrl.length - 1) != '/') {
                        safeBaseUrl += '/';
                    }
                    return safeBaseUrl + safeTopic + safeSessionId;
                };
                ConnectionAgent.prototype.handShake = function () {
                    if (this._handShakeClient != null) {
                        this._handShakeClient.send(this.handShakeConnectUrl, {}, JSON.stringify({
                            "sessionId": this.sessionId,
                            "operation": "hello"
                        }));
                    }
                    else {
                        console.error("HandShake Client Null");
                    }
                };
                ConnectionAgent.prototype.uploadProgram = function (program) {
                    if (program != null) {
                        if (this._ioClient != null) {
                            this._ioClient.send(this.ioConnectUrl, {}, JSON.stringify({
                                "sessionId": this.sessionId,
                                "program": program
                            }));
                        }
                        else {
                            console.error("IO Client Null");
                        }
                    }
                    else {
                        console.error("Program Null");
                    }
                };
                ConnectionAgent.prototype.startRunning = function () {
                    if (this._controlClient != null) {
                        this._controlClient.send(this.controlConnectUrl, {}, JSON.stringify({
                            "sessionId": this.sessionId,
                            "operation": "start"
                        }));
                    }
                    else {
                        console.error("Control Client Null");
                    }
                };
                ConnectionAgent.prototype.pauseRunning = function () {
                    if (this._controlClient != null) {
                        this._controlClient.send(this.controlConnectUrl, {}, JSON.stringify({
                            "sessionId": this.sessionId,
                            "operation": "pause"
                        }));
                    }
                    else {
                        console.error("Control Client Null");
                    }
                };
                ConnectionAgent.prototype.stopRunning = function () {
                    if (this._controlClient != null) {
                        this._controlClient.send(this.controlConnectUrl, {}, JSON.stringify({
                            "sessionId": this.sessionId,
                            "operation": "stop"
                        }));
                    }
                    else {
                        console.error("Control Client Null");
                    }
                };
                ConnectionAgent.prototype.addConnectionSocket = function (socketName) {
                    if (this._connectionSockets.indexOf(socketName) < 0) {
                        this._connectionSockets.push(socketName);
                    }
                };
                ConnectionAgent.prototype.socketConnectionExist = function (socketName) {
                    return (this._connectionSockets.indexOf(socketName) >= 0);
                };
                ConnectionAgent.prototype.connectHandShakeSocket = function () {
                    var _this = this;
                    if (!this.socketConnectionExist("hand_shake")) {
                        var socket = new WebSocket(this.handShakeConnectUrl);
                        this._handShakeClient = stompjs_1.Stomp.over(socket);
                        this._handShakeClient.connect({}, function (frame) {
                            console.log("Connecting HandShake Socket");
                            _this._handShakeClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.handShakeSubscribeUrl, null, _this.sessionId), function (response) {
                                console.log("HandShake Response Call Back");
                                _this.onHandShakeResponse(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("hand_shake");
                        });
                    }
                };
                ConnectionAgent.prototype.connectIoSocket = function () {
                    var _this = this;
                    if (!this.socketConnectionExist("io")) {
                        var socket = new WebSocket(this.ioConnectUrl);
                        this._ioClient = stompjs_1.Stomp.over(socket);
                        this._ioClient.connect({}, function (frame) {
                            _this.addConnectionSocket("io");
                            console.log("Connecting I/O Socket");
                            _this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.ioSubscribeUrl, "memory", _this.sessionId), function (response) {
                                console.log("Memory Update Call Back");
                                _this.onMemoryUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("memory");
                            _this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.ioSubscribeUrl, "register", _this.sessionId), function (response) {
                                console.log("Register Update Call Back");
                                _this.onRegisterUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("register");
                            _this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.ioSubscribeUrl, "instruction", _this.sessionId), function (response) {
                                console.log("Current Instruction Update Call Back");
                                _this.onCurrentInstructionUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("instruction");
                            _this._ioClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.ioSubscribeUrl, "error", _this.sessionId), function (response) {
                                console.log("IO Error Call Back");
                                _this.onError(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("error");
                        });
                    }
                };
                ConnectionAgent.prototype.connectControlSocket = function () {
                    var _this = this;
                    if (!this.socketConnectionExist("control")) {
                        var socket = new WebSocket(this.controlConnectUrl);
                        this._controlClient = stompjs_1.Stomp.over(socket);
                        this._controlClient.connect({}, function (frame) {
                            console.log("Connecting Control Socket");
                            _this.addConnectionSocket("control");
                            _this._controlClient.subscribe(ConnectionAgent.buildSubscribeUrl(_this.controlSubscribeUrl, "error", _this.sessionId), function (response) {
                                console.log("Control Error Call Back");
                                _this.onError(JSON.parse(response.body));
                            });
                        });
                    }
                };
                ConnectionAgent.prototype.connect = function () {
                    this.connectHandShakeSocket();
                    this.connectIoSocket();
                    this.connectControlSocket();
                };
                ConnectionAgent.prototype.disconnect = function () {
                    if (this._controlClient != null) {
                        this._controlClient.disconnect(null);
                        this._controlClient = null;
                        console.log("Control Client Disconnect");
                    }
                    if (this._ioClient != null) {
                        this._ioClient.disconnect(null);
                        this._ioClient = null;
                        console.log("IO Client Disconnect");
                    }
                    if (this._handShakeClient != null) {
                        this._handShakeClient.send(this.handShakeConnectUrl, {}, JSON.stringify({
                            'sessionId': this.sessionId,
                            'operation': 'bye'
                        }));
                        this._handShakeClient.disconnect(null);
                        this._handShakeClient = null;
                        console.log("HandShake Client Disconnect");
                    }
                    this._connectionSockets = [];
                };
                ConnectionAgent.prototype.reset = function () {
                    this.disconnect();
                    this.connect();
                };
                return ConnectionAgent;
            })();
            exports_1("ConnectionAgent", ConnectionAgent);
        }
    }
});
//# sourceMappingURL=connection_agent.js.map