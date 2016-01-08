import {Component} from "../../node_modules/angular2/core.d"
import {ConnectionAgent} from "./connection_agent";
import {Http,HTTP_PROVIDERS} from "../../node_modules/angular2/http.d";


@Component({
    selector: "control-panel",
    templateUrl: "./control-panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class ControlPanel {

    constructor(private connectionAgent: ConnectionAgent) {

    }

    public startOnClick() {
        this.connectionAgent.startRunning();
    }

    public pauseOnClick() {
        this.connectionAgent.pauseRunning();
    }

    public stopOnClick() {
        this.connectionAgent.stopRunning();
    }
}
