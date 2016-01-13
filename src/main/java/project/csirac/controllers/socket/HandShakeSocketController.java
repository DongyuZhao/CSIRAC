package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
import project.csirac.viewmodels.emulator.HandShakeViewModel;

import java.util.Date;

/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
@Controller
public class HandShakeSocketController
{
    private SimpMessagingTemplate template;

    private void setResults(Object ret, String destination)
    {
        template.convertAndSend("/emulator_response/" + destination, ret);
    }

    @Autowired
    public HandShakeSocketController(SimpMessagingTemplate template)
    {
        this.template = template;
    }



    @MessageMapping("/hand_shake")
    public void handShake(HandShakeViewModel model)
    {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        switch (operation)
        {
            case "hello":
                boolean newAdd = !CsiracApplication.sessionExists(sessionId);
                CsiracApplication.sessionList.put(sessionId, (new Date()).getTime());
                boolean stop = false;
                if (newAdd)
                {
                    setResults("Add Session", "hand_shake/" + sessionId);
                    while (true)
                    {
                        if ((new Date()).getTime() - CsiracApplication.sessionList.get(sessionId) > 20000)
                        {
                            CsiracApplication.monitor.stopExecuting(sessionId);
                            CsiracApplication.sessionList.remove(sessionId);
                            stop = true;
                            break;
                        }
                    }
                }
                if (!stop)
                {
                    setResults("Session Working", "hand_shake/" + sessionId);
                }
                else
                {
                    setResults("Session Timeout", "hand_shake/" + sessionId);
                }
                break;
            case "bye":
                if (CsiracApplication.sessionExists(sessionId))
                {
                    CsiracApplication.monitor.stopExecuting(sessionId);
                    CsiracApplication.sessionList.remove(sessionId);
                }
                setResults("Bye", "hand_shake/" + sessionId);
                break;
        }
    }

}
