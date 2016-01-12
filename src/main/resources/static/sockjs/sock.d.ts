declare module "sockjs"
{

    export interface SockJSSimpleEvent
    {
        type: string;
        toString(): string;
    }

    export interface SJSOpenEvent extends SockJSSimpleEvent
    {
    }

    export interface SJSCloseEvent extends SockJSSimpleEvent
    {
        code: number;
        reason: string;
        wasClean: boolean;
    }

    export interface SJSMessageEvent extends SockJSSimpleEvent
    {
        data: string;
    }

    export interface SockJS extends EventTarget
    {
        protocol: string;
        readyState: number;
        onopen: (ev:SJSOpenEvent) => any;
        onmessage: (ev:SJSMessageEvent) => any;
        onclose: (ev:SJSCloseEvent) => any;
        send(data:any): void;
        close(code?:number, reason?:string): void;
        OPEN: number;
        CLOSING: number;
        CONNECTING: number;
        CLOSED: number;
    }

    //export var SockJS:{
    //    prototype: SockJS;
    //    new (url:string, _reserved?:any, options?:{
    //        debug?: boolean;
    //        devel?: boolean;
    //        protocols_whitelist?: string[];
    //        server?: string;
    //        rtt?: number;
    //        rto?: number;
    //        info?: {
    //            websocket?: boolean;
    //            cookie_needed?: boolean;
    //            null_origin?: boolean;
    //        };
    //    }): SockJS;
    //};

    export var SockJs;
}