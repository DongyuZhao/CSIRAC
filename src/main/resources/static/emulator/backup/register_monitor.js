System.register(["../../node_modules/angular2/core.d", "../../node_modules/angular2/http.d"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_d_1, http_d_1;
    var RegisterMonitor;
    return {
        setters:[
            function (core_d_1_1) {
                core_d_1 = core_d_1_1;
            },
            function (http_d_1_1) {
                http_d_1 = http_d_1_1;
            }],
        execute: function() {
            RegisterMonitor = (function () {
                function RegisterMonitor() {
                }
                RegisterMonitor.prototype.updateMemoryView = function (register) {
                    if (register != null) {
                        this.register_view = register;
                    }
                };
                RegisterMonitor = __decorate([
                    core_d_1.Component({
                        selector: 'register-monitor',
                        templateUrl: './register_monitor.html',
                        viewProviders: [http_d_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], RegisterMonitor);
                return RegisterMonitor;
            })();
            exports_1("RegisterMonitor", RegisterMonitor);
        }
    }
});
//# sourceMappingURL=register_monitor.js.map