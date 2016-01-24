import {Component, OnDestroy} from "angular2/core";
import {Stomp, Client, Message, Frame} from 'stompjs'
import {SocketServices} from "../../../services/socket_services";
import {HTTP_PROVIDERS} from "angular2/http";
import {Guid} from "../../../utils/guid"
//import {Ng2Highcharts} from 'ng2-highcharts/ng2-highcharts';

@Component({
    selector: "io-panel",
    templateUrl: "emulator/monitor/io_panel/io_panel.html",
    viewProviders: [HTTP_PROVIDERS]
})

export class IoPanel implements OnDestroy
{
    _host = "localhost:8080/";

    private _client = SocketServices.clientFactory("ws://" + this._host + "/emulator_in/io");

    private _pcRegChartOptions:Object;

    public program = "";

    public structured_program:string[] = [];

    public frequencyView = "";

    public instructionView = "";

    public pcRegView = "";

    public memoryView = [];

    public bitLabel = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20];

    private _pcRegChartView = [];

    public registerView:string[] = [];

    public outputView:string[] = [];

    public statusList:string[] = [];

    public errorList:string[] = [];

    private _sessionId = "";

    private _retryCount = 0;

    public onSubmit()
    {
        console.log("Upload Program");
        if (this.program != null && this.program != "")
        {
            this.structured_program = this.program.split("\n");
        }
        if (this.structured_program != null && this.structured_program.length != 0)
        {
            if (this._client != null && this._client.connected)
            {
                this._client.send("/emulator_in/io", {}, JSON.stringify({
                    "sessionId": this._sessionId,
                    "program": this.structured_program
                }));
                console.log("Upload Finished");
            }
            else
            {
                if (this._retryCount < 3)
                {
                    this.connect();
                    this._client.send("/emulator_in/io", {}, JSON.stringify({
                        "sessionId": this._sessionId,
                        "program": this.structured_program
                    }));
                }
                else
                {
                    this.errorList.push("No Connection");
                }
            }
        }
        else
        {
            console.error("Program Null");
        }
    };

    public onInstructionResponse(response:Message)
    {
        this.instructionView = JSON.parse(response.body);
    };

    public onMemoryResponse(response:Message)
    {
        this.memoryView = JSON.parse(response.body);
    };

    public onRegisterResponse(response:Message)
    {
        this.registerView = JSON.parse(response.body);
    };

    public onPcRegResponse(response:Message)
    {
        this.pcRegView = JSON.parse(response.body);
        var list = this.pcRegView.split("");
        this._pcRegChartView.push(list);
    }

    public onOutputResponse(response:Message)
    {
        this.outputView = JSON.parse(response.body);
    }

    public onStatusResponse(response:Message)
    {
        if (this.statusList.length > 5)
        {
            this.statusList = [];
        }
        this.statusList.push(JSON.parse(response.body));
    }

    public onError(response:Message)
    {
        this.errorList.push(JSON.parse(response.body));
    };

    private connect()
    {
        if (this._client != null && !this._client.connected)
        {
            try
            {
                this._client.connect({},
                        (frame:Frame) =>
                        {
                            this._client.subscribe("/emulator_response/io/instruction/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Instruction Response");
                                        this.onInstructionResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/memory/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Memory Response");
                                        this.onMemoryResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/register/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Register Response");
                                        this.onRegisterResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/status/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("IO Status");
                                        this.onStatusResponse(response);
                                    });

                            this._client.subscribe("/emulator_response/io/pc_reg/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("PC Reg Response");
                                        this.onPcRegResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/output/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("Output Response");
                                        this.onOutputResponse(response);
                                    });
                            this._client.subscribe("/emulator_response/io/error/" + this._sessionId,
                                    (response:Message) =>
                                    {
                                        console.log("IO Error");
                                        this.onError(response);
                                    });
                        });
                console.log("IO Client Connected");
            }
            catch (e)
            {
                console.error(e)
            }
        }
    };

    private disconnect()
    {
        if (this._client != null && this._client.connected)
        {
            this._client.disconnect();
            console.log("IO Client Disconnect");
        }
    };

    constructor()
    {
        var node = document.getElementById("session_id");
        if (node == null)
        {
            this._sessionId = Guid.newGuid();
        }
        else
        {
            this._sessionId = node.innerText;
            if (node.innerText == null || node.innerText == "")
            {
                this._sessionId = Guid.newGuid();
                node.innerText = this._sessionId;
            }
        }
        this.connect();
        //this.initRegisterChart();
    };

    //initRegisterChart()
    //{
    //    this._pcRegChartOptions = {
    //        title: {
    //            text: 'Monthly Average Temperature',
    //            x: -20 //center
    //        },
    //        subtitle: {
    //            text: 'Source: WorldClimate.com',
    //            x: -20
    //        },
    //        xAxis: {
    //            categories: this.bitLabel,
    //        },
    //        yAxis: {
    //            title: {
    //                text: 'S Register'
    //            },
    //            plotLines: [{
    //                value: 0,
    //                width: 1,
    //                color: '#808080'
    //            }]
    //        },
    //        tooltip: {
    //            valueSuffix: ''
    //        },
    //        legend: {
    //            layout: 'vertical',
    //            align: 'right',
    //            verticalAlign: 'middle',
    //            borderWidth: 0
    //        },
    //        series: [{
    //            name: 'S',
    //            data: [{
    //                name:"S Reg",
    //                data: this._pcRegChartView}]
    //        }]
    //    }
    //}

    ngOnDestroy()
    {
        this.disconnect();
    };
}
