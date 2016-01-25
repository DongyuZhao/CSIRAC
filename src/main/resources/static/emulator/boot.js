System.register(['angular2/platform/browser', "./emulator.component"], function(exports_1) {
    var browser_1, emulator_component_1;
    return {
        setters:[
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (emulator_component_1_1) {
                emulator_component_1 = emulator_component_1_1;
            }],
        execute: function() {
            browser_1.bootstrap(emulator_component_1.EmulatorComponent);
        }
    }
});
//# sourceMappingURL=boot.js.map