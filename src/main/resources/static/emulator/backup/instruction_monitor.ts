import {Component} from "../../node_modules/angular2/core.d";
import {Http,HTTP_PROVIDERS} from "../../node_modules/angular2/http.d";

@Component({
    selector: 'instruction-monitor',
    templateUrl: './instruction-monitor.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class InstructionMonitor {
    instruction_view : string;

    public updateMemoryView(instruction: string) {
        if (instruction != null) {
            this.instruction_view = instruction;
        }
    }
}
