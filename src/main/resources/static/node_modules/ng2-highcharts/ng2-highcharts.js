System.register(['./src/directives/ng2-highcharts'], function(exports_1) {
    var ng2_highcharts_1;
    function exportStar_1(m) {
        var exports = {};
        for(var n in m) {
            if (n !== "default") exports[n] = m[n];
        }
        exports_1(exports);
    }
    return {
        setters:[
            function (ng2_highcharts_1_1) {
                ng2_highcharts_1 = ng2_highcharts_1_1;
                exportStar_1(ng2_highcharts_1_1);
            }],
        execute: function() {
            exports_1("default",{
                directives: [ng2_highcharts_1.Ng2Highcharts]
            });
        }
    }
});
//# sourceMappingURL=ng2-highcharts.js.map