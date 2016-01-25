System.register(["angular2/core", "./handshake/client", "./monitor/client", "./debugger/client"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, client_1, client_2, client_3;
    var EmulatorComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (client_1_1) {
                client_1 = client_1_1;
            },
            function (client_2_1) {
                client_2 = client_2_1;
            },
            function (client_3_1) {
                client_3 = client_3_1;
            }],
        execute: function() {
            EmulatorComponent = (function () {
                function EmulatorComponent() {
                }
                EmulatorComponent = __decorate([
                    core_1.Component({
                        selector: "emulator-client",
                        template: "<monitor></monitor><debugger></debugger><handshake></handshake>",
                        directives: [client_1.HandshakeClient, client_2.MonitorClient, client_3.DebuggerClient]
                    }), 
                    __metadata('design:paramtypes', [])
                ], EmulatorComponent);
                return EmulatorComponent;
            })();
            exports_1("EmulatorComponent", EmulatorComponent);
        }
    }
});
//# sourceMappingURL=emulator.component.js.map