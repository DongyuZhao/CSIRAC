System.register(['../../node_modules/angular2/core.d', "../../node_modules/angular2/http.d", "./connection_agent"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_d_1, http_d_1, connection_agent_1;
    var InputPanel;
    return {
        setters:[
            function (core_d_1_1) {
                core_d_1 = core_d_1_1;
            },
            function (http_d_1_1) {
                http_d_1 = http_d_1_1;
            },
            function (connection_agent_1_1) {
                connection_agent_1 = connection_agent_1_1;
            }],
        execute: function() {
            InputPanel = (function () {
                function InputPanel(connectionAgent) {
                    this.connectionAgent = connectionAgent;
                    this.program = "";
                    this.structured_input = [];
                }
                InputPanel.prototype.processInput = function () {
                    if (this.program != "") {
                        this.structured_input = this.program.split('\n');
                    }
                };
                InputPanel.prototype.onSubmit = function () {
                    this.processInput();
                    this.connectionAgent.uploadProgram(this.structured_input);
                };
                InputPanel = __decorate([
                    core_d_1.Component({
                        selector: "input-panel",
                        templateUrl: "./input-panel.html",
                        viewProviders: [http_d_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [connection_agent_1.ConnectionAgent])
                ], InputPanel);
                return InputPanel;
            })();
            exports_1("InputPanel", InputPanel);
        }
    }
});
//# sourceMappingURL=input_panel.js.map