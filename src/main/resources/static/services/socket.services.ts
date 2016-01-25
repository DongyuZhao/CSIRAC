import { Component, Injectable} from 'angular2/core';
import { Http, HTTP_PROVIDERS } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import {Client, Message, Frame} from 'stompjs';

declare var Stomp;

export class SocketConfig
{
    public clientId:string;
    public host = "localhost";
    public port = ":8080";
    public connectUrl = "";
    public subscribeMap:Map<string, Function[]> = new Map<string, Function[]>();
    public multiClient = true;
    public maxRetryTimes = 5;

    public subscribe(url:string, callback: Function)
    {
        if (!this.subscribeMap.has(url))
        {
            this.subscribeMap.set(url, [callback]);
        }
        if (this.subscribeMap.get(url).indexOf(callback) < 0)
        {
            this.subscribeMap.get(url).push(callback);
        }
    }
}

@Injectable()
export class SocketClient
{
    private _client;

    private _retryCount = 0;

    private static createClient(url:string)
    {
        var webSocket = new WebSocket(url);
        return Stomp.over(webSocket);
    }

    constructor(private config:SocketConfig)
    {
        this._client = SocketClient.createClient("ws://" + config.host + config.port + config.connectUrl);
    }

    public connect()
    {
        if (this._client != null && !this._client.connected)
        {
            try
            {
                this._client.connect({}, (frame:Frame) =>
                {
                    this.subscribe();
                })
            }
            catch (e)
            {
                throw e;
            }
        }
    }

    public push(url:string, data:any)
    {
        this.ensureConnection();
        if (this._client != null && this._client.connected)
        {
            this._client.send(url, {}, JSON.stringify(data));
        }
    }

    public disconnect()
    {
        if (this._client != null && this._client.connected)
        {
            this._client.disconnect();
        }
    }

    private subscribe()
    {
        this.config.subscribeMap.forEach((callbacks, url) =>
        {
            var actualUrl = this.buildActualSocketUrl(url);
            this._client.subscribe(actualUrl,
                (response:Message)=>
                {
                    for (var callback of callbacks)
                    {
                        callback(response);
                    }
                });
        });
    }

    private ensureConnection()
    {
        if (this._client == null)
        {
            this._client = SocketClient.createClient("ws://" + this.config.host + this.config.port + this.config.connectUrl);
        }
        if (!this._client.connected)
        {
            this._retryCount++;
            try
            {
                this.connect();
            }
            catch (e)
            {
                if (this._retryCount < this.config.maxRetryTimes)
                {
                    this.ensureConnection();
                }
            }
        }
        this._retryCount = 0;
    }

    private buildActualSocketUrl(url:string):string
    {
        var replacer = "";
        if (this.config.multiClient)
        {
            replacer = this.config.clientId;
        }
        return url.replace("{{client_id}}", replacer);
    }
}