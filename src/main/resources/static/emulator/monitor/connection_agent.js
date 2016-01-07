System.register(["../../utils/guid", 'stompjs'], function(exports_1) {
    "use strict";
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
                function ConnectionAgent(_handShakeClient, _ioClient, _controlClient) {
                    this._handShakeClient = _handShakeClient;
                    this._ioClient = _ioClient;
                    this._controlClient = _controlClient;
                    this.sessionId = guid_1.Guid.newGuid();
                    this._connectionSockets = [];
                    this.connect();
                }
                // TODO::Add callback for hand shake response
                ConnectionAgent.prototype.onHandShakeResponse = function (message) {
                };
                // TODO::Add callback for memory response
                ConnectionAgent.prototype.onMemoryUpdate = function (message) {
                };
                // TODO::Add callback for register response
                ConnectionAgent.prototype.onRegisterUpdate = function (message) {
                };
                // TODO::Add callback for instruction response
                ConnectionAgent.prototype.onCurrentInstructionUpdate = function (message) {
                };
                // TODO::Add callback for error response
                ConnectionAgent.prototype.onError = function (message) {
                };
                /**
                 * addConnectionSocket
                 *      Add the socket into the connected list if it is not exist in the list.
                 * socketName:string    name of the socket
                 */
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
                        var socket = new WebSocket("emulator_in/hand_shake");
                        this._handShakeClient = stompjs_1.Stomp.over(socket);
                        this._handShakeClient.connect({}, function (frame) {
                            console.log("Connecting HandShake Socket");
                            _this._handShakeClient.subscribe('emulator_response/hand_shake/' + _this.sessionId, function (response) {
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
                        var socket = new WebSocket("emulator_in/io");
                        this._ioClient = stompjs_1.Stomp.over(socket);
                        this._ioClient.connect({}, function (frame) {
                            _this.addConnectionSocket("io");
                            console.log("Connecting I/O Socket");
                            _this._ioClient.subscribe('emulator_response/memory/' + _this.sessionId, function (response) {
                                console.log("Memory Update Call Back");
                                _this.onMemoryUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("memory");
                            _this._ioClient.subscribe('emulator_response/register/' + _this.sessionId, function (response) {
                                console.log("Register Update Call Back");
                                _this.onRegisterUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("register");
                            _this._ioClient.subscribe('emulator_response/instruction/' + _this.sessionId, function (response) {
                                console.log("Current Instruction Update Call Back");
                                _this.onCurrentInstructionUpdate(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("instruction");
                            _this._ioClient.subscribe('emulator_response/error/' + _this.sessionId, function (response) {
                                console.log("Error Call Back");
                                _this.onError(JSON.parse(response.body));
                            });
                            _this.addConnectionSocket("error");
                        });
                    }
                };
                ConnectionAgent.prototype.connectControlSocket = function () {
                    var _this = this;
                    if (!this.socketConnectionExist("control")) {
                        var socket = new WebSocket("emulator_in/control");
                        this._controlClient = stompjs_1.Stomp.over(socket);
                        this._controlClient.connect({}, function (frame) {
                            console.log("Connecting Control Socket");
                            _this.addConnectionSocket("control");
                            _this._controlClient.subscribe('emulator_response/error' + _this.sessionId, function (response) {
                                console.log("Error Call Back");
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
                        console.log("Control Client Disconnect");
                    }
                    if (this._ioClient != null) {
                        this._ioClient.disconnect(null);
                        console.log("IO Client Disconnect");
                    }
                    if (this._handShakeClient != null) {
                        this._handShakeClient.send("/emulator_in/hand_shake", {}, JSON.stringify({
                            'sessionId': this.sessionId,
                            'operation': 'bye'
                        }));
                        this._handShakeClient.disconnect(null);
                        console.log("HandShake Client Disconnect");
                    }
                    this._connectionSockets = [];
                };
                ConnectionAgent.prototype.reset = function () {
                    this.disconnect();
                    this.connect();
                };
                return ConnectionAgent;
            }());
            exports_1("ConnectionAgent", ConnectionAgent);
        }
    }
});
//# sourceMappingURL=connection_agent.js.map