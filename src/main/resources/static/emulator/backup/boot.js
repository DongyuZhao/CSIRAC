System.register(['../../node_modules/angular2/platform/browser.d', './monitor.component.ts'], function(exports_1) {
    var browser_d_1, monitor_component_ts_1;
    return {
        setters:[
            function (browser_d_1_1) {
                browser_d_1 = browser_d_1_1;
            },
            function (monitor_component_ts_1_1) {
                monitor_component_ts_1 = monitor_component_ts_1_1;
            }],
        execute: function() {
            browser_d_1.bootstrap(monitor_component_ts_1.MonitorComponent);
        }
    }
});
//# sourceMappingURL=boot.js.map