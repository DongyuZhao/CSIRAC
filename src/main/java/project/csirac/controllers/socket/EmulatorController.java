package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.helper.StringHelper;
import project.csirac.models.emulator.*;
import project.csirac.viewmodels.emulator.ProgramViewModel;

import java.util.*;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
@Controller
public class EmulatorController
{
    private SimpMessagingTemplate template;
    IMonitor monitor = new Monitor();

    public Map<String, Date> sessionList = new HashMap<>();

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
        setResults(this.monitor.getMemory(sessionId), "memory/" + sessionId);
        setResults(this.monitor.getRegister(sessionId), "register/" + sessionId);
        setResults(this.monitor.getCurrentInstruction(sessionId), "current_instruction/" + sessionId);
    }

    @MessageMapping("/emulator_in/upload_program")
    @SendTo("/emulator_response/")
    public void upload_program(ProgramViewModel model) throws Exception
    {
        if (sessionExists(model.getUserSessionId()))
        {
            this.monitor.loadProgramToMemory(model.getUserSessionId(), model.getProgram());
            updateMonitorView(model.getUserSessionId());
        }
        setResults("Session Not Exists", "error" + model.getUserSessionId());
    }

    @MessageMapping("/emulator_in/start")
    @SendTo("/emulator_response/")
    public void start(String sessionId) throws Exception
    {
        if (sessionExists(sessionId))
        {
            this.monitor.startExecuting(sessionId);
            updateMonitorView(sessionId);
            setResults("Running", "state/" + sessionId);
        }
        setResults("Session Not Exists", "error" + sessionId);
    }

    @MessageMapping("/emulator_in/pause")
    @SendTo("/emulator_response/")
    public void pause(String sessionId) throws Exception
    {
        if (sessionExists(sessionId))
        {
            this.monitor.pauseExecuting(sessionId);
            updateMonitorView(sessionId);
            setResults("Pause", "state/" + sessionId);
        }
        setResults("Session Not Exists", "error" + sessionId);
    }

    @MessageMapping("/emulator_in/stop")
    @SendTo("/emulator_response/")
    public void stop(String sessionId) throws Exception
    {
        if (sessionExists(sessionId))
        {

            this.monitor.stopExecuting(sessionId);
            updateMonitorView(sessionId);
            setResults("Stopped", "state/" + sessionId);
        }
        setResults("Session Not Exists", "error" + sessionId);
    }


    @MessageMapping("/emulator_in/keep_alive")
    @SendTo("/emulator_response/")
    public void keepAlive(String sessionId)
    {
        this.sessionList.put(sessionId, new Date());
        while (true)
        {
            Date now = new Date();
            if (now.getTime() - this.sessionList.get(sessionId).getTime() > 20000)
            {
                this.sessionList.remove(sessionId);
                break;
            }
            setResults("alive", "keep_alive/" + sessionId);
        }
    }

    @MessageMapping("/emulator_in/disconnect")
    @SendTo("/emulator_response/")
    public void disconnect(String sessionId)
    {
        if (this.sessionList.keySet().contains(sessionId))
        {
            this.sessionList.remove(sessionId);
        }
        setResults("bye", "disconnect/" + sessionId);
    }

    private boolean sessionExists(String sessionId)
    {
        if (StringHelper.isNullOrWhiteSpace(sessionId))
        {
            return false;
        }
        return this.sessionList.keySet().contains(sessionId);
    }
}
