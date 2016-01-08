System.register(["angular2/core", "./io_panel/io.panel"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, io_panel_1;
    var MonitorComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (io_panel_1_1) {
                io_panel_1 = io_panel_1_1;
            }],
        execute: function() {
            MonitorComponent = (function () {
                function MonitorComponent() {
                }
                MonitorComponent = __decorate([
                    core_1.Component({
                        selector: 'monitor',
                        template: '<io-panel>loading</io-panel>',
                        directives: [io_panel_1.IoPanel]
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