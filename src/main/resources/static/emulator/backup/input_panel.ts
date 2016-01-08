import {Component} from '../../node_modules/angular2/core.d';
import {NgForm} from '../../node_modules/angular2/common.d';
import {Http,HTTP_PROVIDERS} from "../../node_modules/angular2/http.d";
import {ConnectionAgent} from "./connection_agent";

@Component({
    selector: "input-panel",
    templateUrl: "./input-panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class InputPanel {
    public program = "";

    private structured_input:string[] = [];

    constructor(private connectionAgent: ConnectionAgent) {

    }

    private processInput() {
        if (this.program != "") {
            this.structured_input = this.program.split('\n');
        }
    }

    public onSubmit() {
        this.processInput();
        this.connectionAgent.uploadProgram(this.structured_input);
    }
}
