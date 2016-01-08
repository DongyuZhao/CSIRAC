System.register(['angular2/platform/browser', "./monitor.component"], function(exports_1) {
    var browser_1, monitor_component_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (monitor_component_1_1) {
                monitor_component_1 = monitor_component_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(monitor_component_1.MonitorComponent);
        }
    }
});
//# sourceMappingURL=boot.js.map