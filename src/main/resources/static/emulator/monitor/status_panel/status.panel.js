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
    var StatusPanel;
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
            StatusPanel = (function () {
                function StatusPanel() {
                    var _this = this;
                    this._host = "localhost:8080/";
                    this._Client = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    this._Timer = null;
                    this._Count = 0;
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
                    console.log("Connect Complete");
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
                StatusPanel.prototype.connect = function () {
                    var _this = this;
                    if (this._Client != null && !this._Client.connected) {
                        try {
                            this._Client.connect({}, function (frame) {
                                _this._Client.subscribe("/emulator_response/hand_shake/" + _this._sessionId, function (response) {
                                    console.log("Hand Shake Response");
                                    _this.onResponse(response);
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
                StatusPanel.prototype.disconnect = function () {
                    clearInterval(this._Timer);
                    if (this._Client != null && this._Client.connected) {
                        this._Client.send("/emulator_in/hand_shake", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "bye"
                        }));
                        this._Client.disconnect();
                        console.log(" Client Disconnect");
                    }
                };
                ;
                StatusPanel.prototype.keepSessionActive = function () {
                    var _this = this;
                    console.log("Hand Shake Start");
                    if (this._Client != null && this._Client.connected) {
                        this._Count = this._Count + 1;
                        this._Client.send("/emulator_in/hand_shake", {}, JSON.stringify({
                            "sessionId": this._sessionId,
                            "operation": "hello"
                        }));
                        console.log(" Active:" + this._Count);
                    }
                    else {
                        console.log(this._Count);
                        clearInterval(this._Timer);
                        this._Count = 0;
                        this._Client = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/hand_shake");
                        this.connect();
                        this._Timer = setInterval(function () {
                            _this.keepSessionActive();
                        }, 5000);
                    }
                };
                ;
                StatusPanel.prototype.ngOnDestroy = function () {
                    this.disconnect();
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