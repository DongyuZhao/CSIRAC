package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
import project.csirac.models.emulator.IViewObserver;
import project.csirac.viewmodels.emulator.ProgramViewModel;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
@Controller
public class EmulatorIoSocketController implements IViewObserver
{
    private SimpMessagingTemplate template;

    private void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/io/" + destination, ret);
    }

    private void updateMonitorView(String sessionId)
    {
        setResults(CsiracApplication.monitor.peekPcRegister(sessionId), "pc_reg/" + sessionId);
        setResults(CsiracApplication.monitor.getMemory(sessionId), "memory/" + sessionId);
        setResults(CsiracApplication.monitor.getRegister(sessionId), "register/" + sessionId);
        setResults(CsiracApplication.monitor.getCurrentInstruction(sessionId), "instruction/" + sessionId);
    }

    @Autowired
    public EmulatorIoSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
        if (!CsiracApplication.monitor.isViewObserverAttached(this))
        {
            CsiracApplication.monitor.attachViewObserver(this);
        }
    }



    @MessageMapping("/io")
    public void uploadProgram(ProgramViewModel model) throws Exception
    {
        if (CsiracApplication.sessionExists(model.getSessionId()))
        {
            CsiracApplication.monitor.loadProgramToMemory(model.getSessionId(), model.getProgram());
            updateMonitorView(model.getSessionId());
            String response = "";
            for (String line : model.getProgram())
            {
                response += "\t" + line + "\r\n";
            }
            this.pushStatus(model.getSessionId(), "Program: \r\n"+ response +"Uploaded Successful");
        }
        this.pushError(model.getSessionId(), "Session Not Exists");
    }


    /**
     * Push the updated memory of the session to observer
     *
     * @param sessionId
     *         the session id
     * @param data
     *         the memory data
     */
    @Override
    public void pushUpdatedMemory(String sessionId, String[] data)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(data, "memory/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    /**
     * Push the updated register of the session to observer
     *
     * @param sessionId
     *         the session id
     * @param data
     *         the register data
     */
    @Override
    public void pushUpdatedRegister(String sessionId, String[] data)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(data, "register/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    /**
     * Push current instruction of the session to observer
     *
     * @param sessionId
     *         the session id
     * @param instruction
     *         current instruction
     */
    @Override
    public void pushUpdatedInstruction(String sessionId, String instruction)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(instruction, "instruction/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    /**
     * Push current pc reg of the session to observer
     *
     * @param sessionId
     *         the session id
     * @param pcRegister
     *         current pc reg
     */
    @Override
    public void pushPcRegister(String sessionId, int pcRegister)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults("" + pcRegister, "pc_reg/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    /**
     * Push output of the session to observer
     *
     * @param sessionId the session id
     * @param output output
     */
    @Override
    public void pushOutput(String sessionId, String[] output)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(output, "output/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    public void pushStatus(String sessionId, String status)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(status, "status/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }

    public void pushError(String sessionId, String error)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            setResults(error, "error/" + sessionId);
        }
        else
        {
            CsiracApplication.monitor.stopExecuting(sessionId);
        }
    }
}
