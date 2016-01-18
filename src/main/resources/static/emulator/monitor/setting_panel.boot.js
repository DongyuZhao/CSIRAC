System.register(['angular2/platform/browser', "./setting_panel/setting.panel"], function(exports_1) {
    var browser_1, setting_panel_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (setting_panel_1_1) {
                setting_panel_1 = setting_panel_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(setting_panel_1.SettingPanel);
        }
    }
});
//# sourceMappingURL=setting_panel.boot.js.map