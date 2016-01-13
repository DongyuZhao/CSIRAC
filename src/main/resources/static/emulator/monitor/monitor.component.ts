import {Component, Directive} from "angular2/core";
import {IoPanel} from "./io_panel/io.panel";
import {StatusPanel} from "./status_panel/status.panel";
import {Guid} from "../../utils/guid"
import {ControlPanel} from "./control_panel/control.panel";

@Component({
    selector: 'monitor',
    template: '<div id="session_id"></div><io-panel>loading</io-panel><control-panel>loading</control-panel><status-panel>loading</status-panel>',
    directives: [IoPanel, StatusPanel,ControlPanel]
})

export class MonitorComponent
{

}
