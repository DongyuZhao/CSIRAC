import {Component, Directive} from "angular2/core";
import {IoPanel} from "./io_panel/io.panel";

@Component({
    selector: 'monitor',
    template: '<io-panel>loading</io-panel>',
    directives: [IoPanel]
})

export class MonitorComponent {

}