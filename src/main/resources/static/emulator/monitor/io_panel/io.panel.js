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
            //import {Ng2Highcharts} from 'ng2-highcharts/ng2-highcharts';
            IoPanel = (function () {
                function IoPanel() {
                    this._host = "localhost:8080/";
                    this._client = socket_services_1.SocketServices.clientFactory("ws://" + this._host + "/emulator_in/io");
                    this.program = "";
                    this.structured_program = [];
                    this.frequencyView = "";
                    this.instructionView = "";
                    this.pcRegView = "";
                    this.memoryView = [];
                    this.bitLabel = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20];
                    this._pcRegChartView = [];
                    this.registerView = [];
                    this.outputView = [];
                    this.statusList = [];
                    this.errorList = [];
                    this._sessionId = "";
                    this._retryCount = 0;
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
                    //this.initRegisterChart();
                }
                IoPanel.prototype.onSubmit = function () {
                    console.log("Upload Program");
                    if (this.program != null && this.program != "") {
                        this.structured_program = this.program.split("\n");
                    }
                    if (this.structured_program != null && this.structured_program.length != 0) {
                        if (this._client != null && this._client.connected) {
                            this._client.send("/emulator_in/io", {}, JSON.stringify({
                                "sessionId": this._sessionId,
                                "program": this.structured_program
                            }));
                            console.log("Upload Finished");
                        }
                        else {
                            if (this._retryCount < 3) {
                                this.connect();
                                this._client.send("/emulator_in/io", {}, JSON.stringify({
                                    "sessionId": this._sessionId,
                                    "program": this.structured_program
                                }));
                            }
                            else {
                                this.errorList.push("No Connection");
                            }
                        }
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
                    var list = this.pcRegView.split("");
                    this._pcRegChartView.push(list);
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
                IoPanel.prototype.connect = function () {
                    var _this = this;
                    if (this._client != null && !this._client.connected) {
                        try {
                            this._client.connect({}, function (frame) {
                                _this._client.subscribe("/emulator_response/io/instruction/" + _this._sessionId, function (response) {
                                    console.log("Instruction Response");
                                    _this.onInstructionResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/memory/" + _this._sessionId, function (response) {
                                    console.log("Memory Response");
                                    _this.onMemoryResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/register/" + _this._sessionId, function (response) {
                                    console.log("Register Response");
                                    _this.onRegisterResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/status/" + _this._sessionId, function (response) {
                                    console.log("IO Status");
                                    _this.onStatusResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/pc_reg/" + _this._sessionId, function (response) {
                                    console.log("PC Reg Response");
                                    _this.onPcRegResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/output/" + _this._sessionId, function (response) {
                                    console.log("Output Response");
                                    _this.onOutputResponse(response);
                                });
                                _this._client.subscribe("/emulator_response/io/error/" + _this._sessionId, function (response) {
                                    console.log("IO Error");
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
                IoPanel.prototype.disconnect = function () {
                    if (this._client != null && this._client.connected) {
                        this._client.disconnect();
                        console.log("IO Client Disconnect");
                    }
                };
                ;
                ;
                //initRegisterChart()
                //{
                //    this._pcRegChartOptions = {
                //        title: {
                //            text: 'Monthly Average Temperature',
                //            x: -20 //center
                //        },
                //        subtitle: {
                //            text: 'Source: WorldClimate.com',
                //            x: -20
                //        },
                //        xAxis: {
                //            categories: this.bitLabel,
                //        },
                //        yAxis: {
                //            title: {
                //                text: 'S Register'
                //            },
                //            plotLines: [{
                //                value: 0,
                //                width: 1,
                //                color: '#808080'
                //            }]
                //        },
                //        tooltip: {
                //            valueSuffix: ''
                //        },
                //        legend: {
                //            layout: 'vertical',
                //            align: 'right',
                //            verticalAlign: 'middle',
                //            borderWidth: 0
                //        },
                //        series: [{
                //            name: 'S',
                //            data: [{
                //                name:"S Reg",
                //                data: this._pcRegChartView}]
                //        }]
                //    }
                //}
                IoPanel.prototype.ngOnDestroy = function () {
                    this.disconnect();
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