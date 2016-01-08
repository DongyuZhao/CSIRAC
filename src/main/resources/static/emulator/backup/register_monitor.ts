import {Component} from "../../node_modules/angular2/core.d";
import {  Http,HTTP_PROVIDERS  } from "../../node_modules/angular2/http.d";

@Component({
    selector: 'register-monitor',
    templateUrl: './register_monitor.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class RegisterMonitor {
    register_view : string[];

    public updateMemoryView(register:string[]) {
        if (register != null) {
            this.register_view = register;
        }
    }
}
