System.register(["../../node_modules/angular2/core.d", "./instruction_monitor", "./memory_monitor", "./register_monitor", "./connection_agent", "./control_panel", "./input_panel"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_d_1, instruction_monitor_1, memory_monitor_1, register_monitor_1, connection_agent_1, control_panel_1, input_panel_1;
    var MonitorComponent;
    return {
        setters:[
            function (core_d_1_1) {
                core_d_1 = core_d_1_1;
            },
            function (instruction_monitor_1_1) {
                instruction_monitor_1 = instruction_monitor_1_1;
            },
            function (memory_monitor_1_1) {
                memory_monitor_1 = memory_monitor_1_1;
            },
            function (register_monitor_1_1) {
                register_monitor_1 = register_monitor_1_1;
            },
            function (connection_agent_1_1) {
                connection_agent_1 = connection_agent_1_1;
            },
            function (control_panel_1_1) {
                control_panel_1 = control_panel_1_1;
            },
            function (input_panel_1_1) {
                input_panel_1 = input_panel_1_1;
            }],
        execute: function() {
            MonitorComponent = (function () {
                function MonitorComponent() {
                    var _this = this;
                    this.statues = "";
                    this.error = "";
                    this._inputPanel = null;
                    this._controlPanel = null;
                    this._instructionMonitor = new instruction_monitor_1.InstructionMonitor();
                    this._memoryMonitor = new memory_monitor_1.MemoryMonitor();
                    this._registerMonitor = new register_monitor_1.RegisterMonitor();
                    this._connectionAgent = null;
                    this._connectionAgent = new connection_agent_1.ConnectionAgent(function (response) {
                        var body = JSON.parse(response.body).content;
                        _this.onHandShake(body);
                    }, function (response) {
                        var body = JSON.parse(response.body).content;
                        _this._memoryMonitor.updateMemoryView(body);
                    }, function (response) {
                        var body = JSON.parse(response.body).content;
                        _this._registerMonitor.updateMemoryView(body);
                    }, function (response) {
                        var body = JSON.parse(response.body).content;
                        _this._instructionMonitor.updateMemoryView(body);
                    }, function (response) {
                        var body = JSON.parse(response.body).content;
                        _this.onError(body);
                    });
                    this._inputPanel = new input_panel_1.InputPanel(this._connectionAgent);
                    this._controlPanel = new control_panel_1.ControlPanel(this._connectionAgent);
                }
                MonitorComponent.prototype.onHandShake = function (response) {
                    if (response != null) {
                        this.statues = response.body;
                    }
                };
                MonitorComponent.prototype.onError = function (response) {
                    if (response != null) {
                        this.error = response.body;
                    }
                };
                MonitorComponent = __decorate([
                    core_d_1.Component({
                        selector: 'monitor',
                        template: "<div>\n                   <p>{{statues}}</p>\n                   <p>{{error}}</p>\n                   <input-panel></input-panel>\n                   <control-panel></control-panel>\n                   <instrcution-monitor></instrcution-monitor>\n                   <memory-monitor></memory-monitor>\n                   <register-monitor></register-monitor>\n               </div>",
                        directives: [input_panel_1.InputPanel, control_panel_1.ControlPanel, instruction_monitor_1.InstructionMonitor, memory_monitor_1.MemoryMonitor, register_monitor_1.RegisterMonitor]
                    }), 
                    __metadata('design:paramtypes', [])
                ], MonitorComponent);
                return MonitorComponent;
            })();
            exports_1("MonitorComponent", MonitorComponent);
        }
    }
});
//# sourceMappingURL=monitor.component.js.map