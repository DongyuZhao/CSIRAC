package project.csirac.website.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.website.CsiracApplication;
import project.csirac.website.viewmodels.emulator.handshake.HandShakeViewModel;


/**
 * Created by Dy.Zhao on 2016/1/14 0014.
 */
@Controller
public class EmulatorHandShakeSocketController {
    private SimpMessagingTemplate template;

    /**
     * send object in JSON format to the destination url
     *
     * @param ret the object
     * @param destination the destination url
     */
    private void setResults(Object ret, String destination) {
        template.convertAndSend("/emulator_response/handshake/" + destination, ret);
    }

    @Autowired
    public EmulatorHandShakeSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }


    /**
     * receive the heart beat pulse and make sure the emulator status exists when we wish and destroy when we disconnect
     *
     * @param model the handshake view model
     */
    @MessageMapping("/handshake")
    public void handshake(HandShakeViewModel model) {
        String sessionId = model.getSessionId();
        String operation = model.getOperation();
        switch (operation) {
            case "hello":
                boolean newAdd = !CsiracApplication.sessionExists(sessionId);
                boolean stop = false;
                if (newAdd) {
                    setResults("Add Session", sessionId);
                    CsiracApplication.addSession(sessionId);
                    stop = waitTimeout(sessionId);
                } else {
                    CsiracApplication.updateSessionLife(sessionId);
                }
                pushSessionStatus(sessionId, stop);
                break;
            case "bye":
                if (CsiracApplication.sessionExists(sessionId)) {
                    CsiracApplication.monitor.stop(sessionId);
                    CsiracApplication.removeSession(sessionId);
                }
                setResults("Bye", sessionId);
                break;
            default:
                setResults("Unknown Operation", "error/" + sessionId);
                break;
        }
    }

    /**
     * send the response of the session status.
     *
     * @param sessionId
     * @param stop
     */
    private void pushSessionStatus(String sessionId, boolean stop) {
        if (!stop) {
            setResults("Session Working", sessionId);
        } else {
            setResults("Session Timeout", sessionId);
        }
    }

    /**
     * when the wait time of the specified session has out of time
     *
     * @param sessionId the id of the session
     * @return if it is removed
     */
    private boolean waitTimeout(String sessionId) {
        while (true) {
            if (CsiracApplication.getSessionWaitSpan(sessionId) > 20000) {
                CsiracApplication.monitor.stop(sessionId);
                CsiracApplication.removeSession(sessionId);
                break;
            }
        }
        return true;
    }
}
