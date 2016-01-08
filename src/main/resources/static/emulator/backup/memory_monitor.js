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
    var MemoryMonitor;
    return {
        setters:[
            function (core_d_1_1) {
                core_d_1 = core_d_1_1;
            },
            function (http_d_1_1) {
                http_d_1 = http_d_1_1;
            }],
        execute: function() {
            MemoryMonitor = (function () {
                function MemoryMonitor() {
                }
                MemoryMonitor.prototype.updateMemoryView = function (memory) {
                    if (memory != null) {
                        this.memory_view = memory;
                    }
                };
                MemoryMonitor = __decorate([
                    core_d_1.Component({
                        selector: 'memory-monitor',
                        templateUrl: './memory_monitor.html',
                        viewProviders: [http_d_1.HTTP_PROVIDERS]
                    }), 
                    __metadata('design:paramtypes', [])
                ], MemoryMonitor);
                return MemoryMonitor;
            })();
            exports_1("MemoryMonitor", MemoryMonitor);
        }
    }
});
//# sourceMappingURL=memory_monitor.js.map