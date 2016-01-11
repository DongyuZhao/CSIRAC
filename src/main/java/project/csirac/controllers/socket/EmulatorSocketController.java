package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.helper.StringHelper;
import project.csirac.models.emulator.*;
import project.csirac.viewmodels.emulator.ControlOperationViewModel;
import project.csirac.viewmodels.emulator.HandShakeViewModel;
import project.csirac.viewmodels.emulator.ProgramViewModel;

import java.util.*;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
@Controller
public class EmulatorSocketController implements IMonitorObserver
{
    private SimpMessagingTemplate template;

    IMonitor monitor = new Monitor(this);

    public Map<String, Long> sessionList = new HashMap<>();

    @Autowired
    public EmulatorSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
    }

    void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/" + destination, ret);
    }

    private void updateMonitorView(String sessionId)
    {
        setResults(this.monitor.getMemory(sessionId), "io/memory/" + sessionId);
        setResults(this.monitor.getRegister(sessionId), "io/register/" + sessionId);
        setResults(this.monitor.getCurrentInstruction(sessionId), "io/instruction/" + sessionId);
    }

    private boolean sessionExists(String sessionId)
    {
        if (StringHelper.isNullOrWhiteSpace(sessionId))
        {
            return false;
        }
        return this.sessionList.keySet().contains(sessionId);
    }

    @MessageMapping("/emulator_in/io")
    public void uploadProgram(ProgramViewModel model) throws Exception
    {
        if (sessionExists(model.getSessionId()))
        {
            this.monitor.loadProgramToMemory(model.getSessionId(), model.getProgram());
            updateMonitorView(model.getSessionId());
        }
        setResults("Session Not Exists", "io/error" + model.getSessionId());
    }

    @MessageMapping("/emulator_in/control")
    public void control(ControlOperationViewModel model)
    {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        if (sessionExists(sessionId))
        {
            switch (operation)
            {
                case "start":
                    //updateMonitorView(sessionId);
                    this.monitor.startExecuting(sessionId);
                    break;
                case "pause":
                    //updateMonitorView();
                    this.monitor.pauseExecuting(sessionId);
                    break;
                case "stop":
                    this.monitor.stopExecuting(sessionId);
                    break;
            }
        }
        else
        {
            setResults("Session Not Exists", "control/error" + sessionId);
        }
    }

    @MessageMapping("/emulator_in/hand_shake")
    public void handShake(HandShakeViewModel model)
    {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        switch (operation)
        {
            case  "hello" :
                boolean newAdd = !sessionExists(sessionId);
                this.sessionList.put(sessionId, (new Date()).getTime());
                if (newAdd)
                {
                    while(true)
                    {
                        if ((new Date()).getTime() - this.sessionList.get(sessionId) > 20000)
                        {
                            this.sessionList.remove(sessionId);
                            break;
                        }
                        setResults("alive", "hand_shake/" + sessionId);
                    }
                }
                break;
            case "bye" :
                if (this.sessionExists(sessionId))
                {
                    this.monitor.stopExecuting(sessionId);
                    this.sessionList.remove(sessionId);
                }
                setResults("bye", "hand_shake/" + sessionId);
                break;
        }
    }

    @Override
    public void updateMemoryView(String sessionId, String[] data)
    {
        if (sessionExists(sessionId))
        {
            setResults(data, "memory/" + sessionId);
        }
        else
        {
            this.monitor.stopExecuting(sessionId);
        }
    }

    @Override
    public void updateRegisterView(String sessionId, String[] data)
    {
        if (sessionExists(sessionId))
        {
            setResults(data, "register/" + sessionId);
        }
        else
        {
            this.monitor.stopExecuting(sessionId);
        }
    }

    @Override
    public void updateInstructionView(String sessionId, String instruction)
    {
        if (sessionExists(sessionId))
        {
            setResults(instruction, "instruction/" + sessionId);
        }
        else
        {
            this.monitor.stopExecuting(sessionId);
        }
    }
}
