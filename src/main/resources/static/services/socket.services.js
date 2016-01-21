System.register(['angular2/core'], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1;
    var SocketConfig, SocketClient;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            SocketConfig = (function () {
                function SocketConfig() {
                    this.host = "localhost";
                    this.port = ":8080";
                    this.connectUrl = "";
                    this.subscribeMap = new Map();
                    this.multiClient = true;
                    this.maxRetryTimes = 5;
                }
                SocketConfig.prototype.subscribe = function (url, callback) {
                    if (!this.subscribeMap.has(url)) {
                        this.subscribeMap.set(url, [callback]);
                    }
                    if (this.subscribeMap.get(url).indexOf(callback) < 0) {
                        this.subscribeMap.get(url).push(callback);
                    }
                };
                return SocketConfig;
            })();
            exports_1("SocketConfig", SocketConfig);
            SocketClient = (function () {
                function SocketClient(config) {
                    this.config = config;
                    this._retryCount = 0;
                    this._client = SocketClient.createClient("ws://" + config.host + config.port + config.connectUrl);
                }
                SocketClient.createClient = function (url) {
                    var webSocket = new WebSocket(url);
                    return Stomp.over(webSocket);
                };
                SocketClient.prototype.connect = function () {
                    var _this = this;
                    if (this._client != null && !this._client.connected) {
                        try {
                            this._client.connect({}, function (frame) {
                                _this.subscribe();
                            });
                        }
                        catch (e) {
                            throw e;
                        }
                    }
                };
                SocketClient.prototype.push = function (url, data) {
                    this.ensureConnection();
                    if (this._client != null && this._client.connected) {
                        this._client.send(url, {}, JSON.stringify(data));
                    }
                };
                SocketClient.prototype.disconnect = function () {
                    if (this._client != null && this._client.connected) {
                        this._client.disconnect();
                    }
                };
                SocketClient.prototype.subscribe = function () {
                    var _this = this;
                    this.config.subscribeMap.forEach(function (callbacks, url) {
                        var actualUrl = _this.buildActualSocketUrl(url);
                        _this._client.subscribe(actualUrl, function (response) {
                            for (var _i = 0; _i < callbacks.length; _i++) {
                                var callback = callbacks[_i];
                                callback(response);
                            }
                        });
                    });
                };
                SocketClient.prototype.ensureConnection = function () {
                    if (this._client == null) {
                        this._client = SocketClient.createClient("ws://" + this.config.host + this.config.port + this.config.connectUrl);
                    }
                    if (!this._client.connected) {
                        this._retryCount++;
                        try {
                            this.connect();
                        }
                        catch (e) {
                            if (this._retryCount < this.config.maxRetryTimes) {
                                this.ensureConnection();
                            }
                        }
                    }
                    this._retryCount = 0;
                };
                SocketClient.prototype.buildActualSocketUrl = function (url) {
                    var replacer = "";
                    if (this.config.multiClient) {
                        replacer = this.config.clientId;
                    }
                    return url.replace("{{client_id}}", replacer);
                };
                SocketClient = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [SocketConfig])
                ], SocketClient);
                return SocketClient;
            })();
            exports_1("SocketClient", SocketClient);
        }
    }
});
//# sourceMappingURL=socket.services.js.map