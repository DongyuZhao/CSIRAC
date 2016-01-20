System.register(['angular2/platform/browser', "./control_panel/control.panel"], function(exports_1) {
    var browser_1, control_panel_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (control_panel_1_1) {
                control_panel_1 = control_panel_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(control_panel_1.ControlPanel);
        }
    }
});
//# sourceMappingURL=control_panel.boot.js.map