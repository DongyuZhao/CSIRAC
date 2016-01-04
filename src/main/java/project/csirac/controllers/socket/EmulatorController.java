package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.models.emulator.*;
import project.csirac.viewmodels.emulator.ProgramViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
@Controller
public class EmulatorController
{
    private SimpMessagingTemplate template;
    IMonitor monitor = new Monitor();

    public List<String> sessionList = new ArrayList<>();

    @Autowired
    public EmulatorController(SimpMessagingTemplate template)
    {
        this.template = template;
    }

    void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/" + destination, ret);
    }

    private void updateMonitorView(String sessionId)
    {
        setResults(this.monitor.getMemory(sessionId),"memory/" + sessionId);
        setResults(this.monitor.getRegister(sessionId),"register/" + sessionId);
        setResults(this.monitor.getCurrentInstruction(sessionId),"current_instruction/" + sessionId);
    }

    @MessageMapping("/emulator_in/upload_program")
    @SendTo("/emulator_response/")
    public void greeting(ProgramViewModel model) throws Exception
    {
        this.monitor.loadProgramToMemory(model.getUserSessionId(), model.getProgram());
        updateMonitorView(model.getUserSessionId());
    }

    @MessageMapping("/emulator_in/keep_alive")
    @SendTo("/emulator_response/")
    public void keepAlive(String sessionId)
    {
        if (!this.sessionList.contains(sessionId))
        {
            this.sessionList.add(sessionId);
        }
        while (this.sessionList.contains(sessionId))
        {
            setResults("alive","keep_alive/" + sessionId);
        }
    }

    @MessageMapping("/emulator_in/keep_alive")
    @SendTo("/emulator_response/")
    public void disconnect(String sessionId)
    {
        if (this.sessionList.contains(sessionId))
        {
            this.sessionList.remove(sessionId);
        }
        setResults("bye", "disconnect/" + sessionId);
    }
}
