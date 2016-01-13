System.register([], function(exports_1) {
    var Settings;
    return {
        setters:[],
        execute: function() {
            Settings = (function () {
                function Settings(sessionId, frequency) {
                    this.sessionId = "";
                    this.frequency = 100;
                    this.sessionId = sessionId;
                    this.frequency = frequency;
                }
                return Settings;
            })();
            exports_1("Settings", Settings);
        }
    }
});
//# sourceMappingURL=settings.js.map