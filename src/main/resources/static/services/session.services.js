System.register(["../utils/guid"], function(exports_1) {
    var guid_1;
    var SessionServices;
    return {
        setters:[
            function (guid_1_1) {
                guid_1 = guid_1_1;
            }],
        execute: function() {
            SessionServices = (function () {
                function SessionServices() {
                }
                SessionServices.ensureSessionId = function (client) {
                    var node = document.getElementById("session_id");
                    if (node == null) {
                        client._sessionId = guid_1.Guid.newGuid();
                    }
                    else {
                        client._sessionId = node.innerText;
                        if (node.innerText == null || node.innerText == "") {
                            client._sessionId = guid_1.Guid.newGuid();
                            node.innerText = client._sessionId;
                        }
                    }
                };
                return SessionServices;
            })();
            exports_1("SessionServices", SessionServices);
        }
    }
});
//# sourceMappingURL=session.services.js.map