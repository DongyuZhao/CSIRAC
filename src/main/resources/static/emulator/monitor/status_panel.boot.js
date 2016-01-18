System.register(['angular2/platform/browser', "./status_panel/status.panel"], function(exports_1) {
    var browser_1, status_panel_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (status_panel_1_1) {
                status_panel_1 = status_panel_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(status_panel_1.StatusPanel);
        }
    }
});
//# sourceMappingURL=status_panel.boot.js.map