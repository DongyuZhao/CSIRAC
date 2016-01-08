import {Component} from "../../node_modules/angular2/core.d";
import {Http,HTTP_PROVIDERS} from "../../node_modules/angular2/http.d";

@Component({
    selector: 'memory-monitor',
    templateUrl: './memory_monitor.html',
    viewProviders: [HTTP_PROVIDERS]
})

export class MemoryMonitor {
    memory_view : string[];

    public updateMemoryView(memory:string[]) {
        if (memory != null) {
            this.memory_view = memory;
        }
    }
}
