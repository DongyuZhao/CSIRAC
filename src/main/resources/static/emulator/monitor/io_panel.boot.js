System.register(['angular2/platform/browser', "./io_panel/io.panel"], function(exports_1) {
    var browser_1, io_panel_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (io_panel_1_1) {
                io_panel_1 = io_panel_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(io_panel_1.IoPanel);
        }
    }
});
//# sourceMappingURL=io_panel.boot.js.map