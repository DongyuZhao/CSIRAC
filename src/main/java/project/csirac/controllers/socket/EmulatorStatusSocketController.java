package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
import project.csirac.models.emulator.*;
import project.csirac.viewmodels.emulator.ControlOperationViewModel;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
@Controller
public class EmulatorStatusSocketController implements IStatusObserver
{
    private SimpMessagingTemplate template;

    private void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/control/" + destination, ret);
    }



    @Autowired
    public EmulatorStatusSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
        if (!CsiracApplication.monitor.isStatusObserverAttached(this))
        {
            CsiracApplication.monitor.attachStatusObserver(this);
        }
    }

    @MessageMapping("/control")
    public void control(ControlOperationViewModel model)
    {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        if (CsiracApplication.sessionExists(sessionId))
        {
            switch (operation)
            {
                case "start":
                    CsiracApplication.monitor.startExecuting(sessionId);
                    //setResults("Running", "control/status/" + sessionId);
                    break;
                case "pause":
                    CsiracApplication.monitor.pauseExecuting(sessionId);
                    //setResults("Pause", "control/status/" + sessionId);
                    break;
                case "next":
                    CsiracApplication.monitor.nextInstruction(sessionId);
                    //setResults("Pause", "control/status/" + sessionId);
                    break;
                case "continue":
                    CsiracApplication.monitor.continueExecuting(sessionId);
                    //setResults("Running", "control/status/" + sessionId);
                    break;
                case "stop":
                    CsiracApplication.monitor.stopExecuting(sessionId);
                    //setResults("Stop", "control/status/" + sessionId);
                    break;
                case "check":
                    this.pushReady(sessionId);
                    break;
                default:
                    this.pushError( sessionId, "Unknown Operation");
                    break;
            }
        }
        else
        {
            if (operation.equals("check"))
            {
                this.pushStatus(sessionId, "Not Ready");
            }
            else
            {
                this.pushError( sessionId, "Session Not Exists");
            }
        }
    }


    /**
     * Push running status of the session to observer
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void pushRunning(String sessionId)
    {
        this.pushStatus(sessionId, "Running");
    }

    /**
     * Push pause status of the session to observer
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void pushPause(String sessionId)
    {
        this.pushStatus(sessionId, "Pause");
    }

    /**
     * Push stop status of the session to observer
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void pushStop(String sessionId)
    {
        this.pushStatus(sessionId, "Stop");
    }


    /**
     * Push the ready info of the session to observer
     *
     * @param sessionId
     *         the session id
     */
    @Override
    public void pushReady(String sessionId)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            this.pushStatus(sessionId, "Ready");
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
