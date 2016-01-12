package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
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

    public Map<String, Long> sessionList = new HashMap<>();

    @Autowired
    public EmulatorSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
        if (!CsiracApplication.monitor.isObserverAttached(this))
        {
            CsiracApplication.monitor.attachObserver(this);
        }
    }

    void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/" + destination, ret);
    }

    private void updateMonitorView(String sessionId)
    {
        setResults(CsiracApplication.monitor.getMemory(sessionId), "io/memory/" + sessionId);
        setResults(CsiracApplication.monitor.getRegister(sessionId), "io/register/" + sessionId);
        setResults(CsiracApplication.monitor.getCurrentInstruction(sessionId), "io/instruction/" + sessionId);
    }

    private boolean sessionExists(String sessionId)
    {
        if (StringHelper.isNullOrWhiteSpace(sessionId))
        {
            return false;
        }
        return this.sessionList.keySet().contains(sessionId);
    }

    @MessageMapping("/io")
    public void uploadProgram(ProgramViewModel model) throws Exception
    {
        if (sessionExists(model.getSessionId()))
        {
            CsiracApplication.monitor.loadProgramToMemory(model.getSessionId(), model.getProgram());
            updateMonitorView(model.getSessionId());
        }
        setResults("Session Not Exists", "io/error" + model.getSessionId());
    }

    @MessageMapping("/test_sockjs")
    public void test(String a)
    {
        while(true)
        {
            for (int i = 0; i < 100000; i++);
            setResults("Test Response", "test");
        }
    }

    @MessageMapping("/control")
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
                    CsiracApplication.monitor.startExecuting(sessionId);
                    break;
                case "pause":
                    //updateMonitorView();
                    CsiracApplication.monitor.pauseExecuting(sessionId);
                    break;
                case "stop":
                    CsiracApplication.monitor.stopExecuting(sessionId);
                    break;
            }
        }
        else
        {
            setResults("Session Not Exists", "control/error" + sessionId);
        }
    }

    @MessageMapping("/hand_shake")
    public void handShake(HandShakeViewModel model)
    {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        switch (operation)
        {
            case  "hello" :
                boolean newAdd = !sessionExists(sessionId);
                this.sessionList.put(sessionId, (new Date()).getTime());
                setResults("Session Working", "hand_shake/" + sessionId);
                if (newAdd)
                {
                    while(true)
                    {
                        if ((new Date()).getTime() - this.sessionList.get(sessionId) > 20000)
                        {
                            CsiracApplication.monitor.stopExecuting(sessionId);
                            this.sessionList.remove(sessionId);
                            break;
                        }
                    }
                }
                break;
            case "bye" :
                if (this.sessionExists(sessionId))
                {
                    CsiracApplication.monitor.stopExecuting(sessionId);
                    this.sessionList.remove(sessionId);
                }
                setResults("Bye", "hand_shake/" + sessionId);
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
            CsiracApplication.monitor.stopExecuting(sessionId);
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
            CsiracApplication.monitor.stopExecuting(sessionId);
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
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }
}
