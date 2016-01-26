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

    @MessageMapping("/monitor/input/program")
    public void uploadProgram(ProgramViewModel model) {
        if (model != null) {
            String sessionId = model.getSessionId();
            if (CsiracApplication.sessionExists(sessionId)) {
                CsiracApplication.monitor.loadProgram(sessionId, model.getProgram());
            }
        }
    }

    @MessageMapping("/monitor/input/data")
    public void uploadData(DataViewModel model) {
        if (model != null) {
            String sessionId = model.getSessionId();
            if (CsiracApplication.sessionExists(sessionId)) {
                CsiracApplication.monitor.loadData(sessionId, model.getData());
            }
        }
    }

    @MessageMapping("/monitor/control")
    public void start(ControlViewModel model) {
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

    public void pushReady(String sessionId) {
        if (CsiracApplication.sessionExists(sessionId)) {
            this.pushStatus(sessionId, "Ready");
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }

    public void pushStatus(String sessionId, String status) {
        if (CsiracApplication.sessionExists(sessionId)) {
            setResults(status, "control/status/" + sessionId);
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }

    public void pushError(String sessionId, String error) {
        if (CsiracApplication.sessionExists(sessionId)) {
            setResults(error, "error/" + sessionId);
        } else {
            CsiracApplication.monitor.stop(sessionId);
        }
    }
}
