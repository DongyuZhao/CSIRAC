import {Component} from "../../node_modules/angular2/core.d"
import {InstructionMonitor} from "./instruction_monitor";
import {MemoryMonitor} from "./memory_monitor";
import {RegisterMonitor} from "./register_monitor";
import {ConnectionAgent} from "./connection_agent";
import {ControlPanel} from "./control_panel";
import {InputPanel} from "./input_panel";
import {Message} from "stompjs";

@Component({
    selector: 'monitor',
    template: `<div>
                   <p>{{statues}}</p>
                   <p>{{error}}</p>
                   <input-panel></input-panel>
                   <control-panel></control-panel>
                   <instrcution-monitor></instrcution-monitor>
                   <memory-monitor></memory-monitor>
                   <register-monitor></register-monitor>
               </div>`,
    directives:[InputPanel,ControlPanel,InstructionMonitor,MemoryMonitor,RegisterMonitor]
})

export class MonitorComponent {
    statues = "";
    error = "";
    private _inputPanel : InputPanel = null;
    private _controlPanel : ControlPanel = null;

    private _instructionMonitor = new InstructionMonitor();
    private _memoryMonitor = new MemoryMonitor();
    private _registerMonitor = new RegisterMonitor();

    public onHandShake(response:Message) {
        if (response != null) {
            this.statues = response.body;
        }
    }

    public onError(response:Message) {
        if (response != null) {
            this.error = response.body;
        }
    }

    private _connectionAgent : ConnectionAgent = null;
    constructor() {
        this._connectionAgent = new ConnectionAgent(
            (response: Message) => {
                var body = JSON.parse(response.body).content;
                this.onHandShake(body);
            },
            (response: Message) => {
                var body = JSON.parse(response.body).content;
                this._memoryMonitor.updateMemoryView(body)
            },
            (response: Message) => {
                var body = JSON.parse(response.body).content;
                this._registerMonitor.updateMemoryView(body)
            },
            (response: Message) => {
                var body = JSON.parse(response.body).content;
                this._instructionMonitor.updateMemoryView(body)
            },
            (response: Message) => {
                var body = JSON.parse(response.body).content;
                this.onError(body);
            });
        this._inputPanel = new InputPanel(this._connectionAgent);
        this._controlPanel = new ControlPanel(this._connectionAgent);
    }
}
