package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
import project.csirac.models.emulator.ISettingObserver;
import project.csirac.viewmodels.emulator.SettingViewModel;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
@Controller
public class EmulatorSettingSocketController implements ISettingObserver
{
    private SimpMessagingTemplate template;

    private void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/settings/" + destination, ret);
    }

    @Autowired
    public EmulatorSettingSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
        if(!CsiracApplication.monitor.isSettingObserverAttached(this))
        {
            CsiracApplication.monitor.attachSettingObserver(this);
        }
    }

    @MessageMapping("/settings")
    public void setting(SettingViewModel model)
    {
        if (CsiracApplication.sessionExists(model.getSessionId()))
        {
            updateFrequencySetting(model);
        }
        else
        {
            this.pushError(model.getSessionId(), "Session Not Exists");
        }
    }

    private void updateFrequencySetting(SettingViewModel model)
    {
        CsiracApplication.monitor.setClockFrequency(model.getSessionId(), model.getFrequency());
        this.pushStatus(model.getSessionId(), "Setting: \r\n Frequency:"+ model.getFrequency() +"Uploaded Successful");
    }

    /**
     * Update current setting views
     *
     * @param sessionId
     *         the session id
     * @param model
     * the view model
     */
    @Override
    public void pushCurrentSettings(String sessionId, SettingViewModel model)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {

            setResults(model, "current/" + sessionId);
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
