package project.csirac.website.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.website.CsiracApplication;
import project.csirac.website.viewmodels.emulator.debugger.ClockViewModel;
import project.csirac.website.viewmodels.emulator.debugger.PcRegisterViewModel;
import project.emulator.framework.api.debugger.*;

/**
 * Created by Dy.Zhao on 2016/1/24 0024.
 */
@Controller
public class EmulatorDebuggerController implements IDebuggerObserver {
    private SimpMessagingTemplate _template;

    private IDebugger _debugger;


    /**
     * send object in JSON format to the destination url
     *
     * @param ret the object
     * @param destination the destination url
     */
    private void setResults(Object ret, String destination) {
        _template.convertAndSend("/emulator_response/debugger/" + destination, ret);
    }

    @Autowired
    public EmulatorDebuggerController(SimpMessagingTemplate _template) {
        this._template = _template;
        if (!CsiracApplication.debugger.isObserverAttached(this)) {
            CsiracApplication.debugger.attachObserver(this);
        }
    }

    /**
     * change the frequency the instructions should be executed
     *
     * @param model the clock setting view model
     */
    @MessageMapping("/debugger/input/clock")
    public void changeClock(ClockViewModel model) {
        if (model != null) {
            this._debugger.setClock(model.getSessionId(), model.getFrequency());
        }
    }

    /**
     * change the value of the PC Register
     *
     * @param model the PC Reg setting view model
     */
    @MessageMapping("/debugger/input/pc_reg")
    public void updatePcRegister(PcRegisterViewModel model) {
        if (model != null) {
            this._debugger.setPcRegister(model.getSessionId(), model.getAddress());
        }
    }

    @Override
    public void onPcRegisterUpdate(int message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "pc_reg/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void onOpCodeUpdate(int message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "opcode/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void onRegisterUpdate(RegisterMessage message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "register/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void onDataMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "data_memory/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void onProgramMemoryUpdate(MemoryMessage message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "program_memory/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void onFrequencyChange(float message, DebuggerMessageSender messageSender) {
        if (messageSender != null) {
            this.setResults(message, "clock/" + messageSender.getMessageSenderId());
        }
    }

    @Override
    public void attachDebugger(IDebugger debugger) {
        if (debugger != null) {
            this._debugger = debugger;
        }
    }
}
