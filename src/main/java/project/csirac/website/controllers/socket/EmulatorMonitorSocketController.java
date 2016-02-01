package project.csirac.website.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.website.CsiracApplication;
import project.csirac.website.viewmodels.emulator.control.ControlViewModel;
import project.csirac.website.viewmodels.emulator.monitor.DataViewModel;
import project.csirac.website.viewmodels.emulator.monitor.ProgramViewModel;
import project.emulator.framework.api.monitor.CpuMonitorMessageSender;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.monitor.IMonitorObserver;
import project.emulator.framework.api.monitor.MemoryMonitorMessageSender;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
@Controller
public class EmulatorMonitorSocketController implements IMonitorObserver {
    private SimpMessagingTemplate _template;

    private IMonitor _monitor;

    /**
     * send object in JSON format to the destination url
     *
     * @param ret         the object
     * @param destination the destination url
     */
    private void setResults(Object result, String destination) {
        _template.convertAndSend("/emulator_response/monitor/" + destination, result);
    }

    @Autowired
    public EmulatorMonitorSocketController(SimpMessagingTemplate _template) {
        this._template = _template;
        if (!CsiracApplication.monitor.isObserverAttached(this)) {
            CsiracApplication.monitor.attachObserver(this);
        }
    }


    /**
     * load the submitted program into the emulator
     *
     * @param model the program view model
     */
    @MessageMapping("/monitor/input/program")
    public void uploadProgram(ProgramViewModel model) {
        if (model != null) {
            String sessionId = model.getSessionId();
            if (CsiracApplication.sessionExists(sessionId)) {
                CsiracApplication.monitor.loadProgram(sessionId, model.getProgram());
            }
        }
    }

    /**
     * load the submitted data into the emulator
     *
     * @param model the data view model
     */
    @MessageMapping("/monitor/input/data")
    public void uploadData(DataViewModel model) {
        if (model != null) {
            String sessionId = model.getSessionId();
            if (CsiracApplication.sessionExists(sessionId)) {
                CsiracApplication.monitor.loadData(sessionId, model.getData());
            }
        }
    }

    /**
     * perform the action specified by the socket signal
     *
     * @param model the control view model
     */
    @MessageMapping("/monitor/control")
    public void control(ControlViewModel model) {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        if (CsiracApplication.sessionExists(sessionId)) {
            switch (operation) {
                case "start":
                    CsiracApplication.monitor.start(sessionId);
                    //setResults("Running", "control/status/" + sessionId);
                    break;
                case "pause":
                    CsiracApplication.monitor.pause(sessionId);
                    //setResults("Pause", "control/status/" + sessionId);
                    break;
                case "next":
                    CsiracApplication.monitor.next(sessionId);
                    //setResults("Pause", "control/status/" + sessionId);
                    break;
                case "continue":
                    CsiracApplication.monitor.go(sessionId);
                    //setResults("Running", "control/status/" + sessionId);
                    break;
                case "stop":
                    CsiracApplication.monitor.stop(sessionId);
                    //setResults("Stop", "control/status/" + sessionId);
                    break;
                case "check":
                    if (this._monitor.isReady(sessionId)) {
                        this.pushReady(sessionId);
                    }
                    break;
                default:
                    this.pushError(sessionId, "Unknown Operation");
                    break;
            }
        } else {
            if (operation.equals("check")) {
                this.pushStatus(sessionId, "Not Ready");
            } else {
                this.pushError(sessionId, "Session Not Exists");
            }
        }
    }

    @Override
    public void onDataLoaded(MemoryMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Data Loaded");
        }
    }

    @Override
    public void onProgramLoaded(MemoryMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Program Loaded");
        }
    }

    @Override
    public void onStart(CpuMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Running");
        }
    }

    @Override
    public void onPause(CpuMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Pause");
        }
    }

    @Override
    public void onContinue(CpuMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Running");
        }
    }

    @Override
    public void onStop(CpuMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.pushStatus(messageSender.getMessageSenderId(), "Stop");
        }
    }

    @Override
    public void onOutput(String[] data, CpuMonitorMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(data, messageSender.getMessageSenderId());
        }
    }

    @Override
    public void attachMonitor(IMonitor monitor) {
        if (monitor != null) {
            this._monitor = monitor;
        }
    }

    /**
     * send ready signal to the client
     *
     * @param sessionId the id of the receiver session
     */
    public void pushReady(String sessionId) {
        if (CsiracApplication.sessionExists(sessionId)) {
            this.pushStatus(sessionId, "Ready");
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }

    /**
     * send sttatus signal to the client
     *
     * @param sessionId the id of the receiver session
     * @param status    the status
     */
    public void pushStatus(String sessionId, String status) {
        if (CsiracApplication.sessionExists(sessionId)) {
            setResults(status, "control/status/" + sessionId);
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }

    /**
     * send error signal to the client
     *
     * @param sessionId the id of the receiver session
     * @param error     the error message
     */
    public void pushError(String sessionId, String error) {
        if (CsiracApplication.sessionExists(sessionId)) {
            setResults(error, "error/" + sessionId);
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }
}
