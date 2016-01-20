package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.CsiracApplication;
import project.csirac.models.emulator.IViewObserver;
import project.csirac.viewmodels.emulator.ProgramViewModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

//    private void updateMonitorView(String sessionId)
//    {
//        setResults(CsiracApplication.monitor.peekPcRegister(sessionId), "pc_reg/" + sessionId);
//        setResults(CsiracApplication.monitor.getMemory(sessionId), "memory/" + sessionId);
//        setResults(CsiracApplication.monitor.getRegister(sessionId), "register/" + sessionId);
//        setResults(CsiracApplication.monitor.getCurrentInstruction(sessionId), "instruction/" + sessionId);
//    }

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
            CsiracApplication.monitor.loadProgram(model.getSessionId(), model.getProgram());
            // TODO:: delete test code
            //this.pushUpdatedRegister(model.getSessionId(), new HashMap<>());
//            int i = 0;
//            while (i < 500)
//            {
//                if ((new Date()).getTime() - CsiracApplication.sessionList.get(model.getSessionId()) > 1000)
//                {
//                    this.pushPcRegister(model.getSessionId(), generateFakeResult(20));
//                }
//            }
            //this.pushPcRegister(model.getSessionId(), "10011110000111010010");

            //updateMonitorView(model.getSessionId());
            String response = "";
            for (String line : model.getProgram())
            {
                response += "\t" + line + "\r\n";
            }
            this.pushStatus(model.getSessionId(), "Program: \r\n" + response + "Uploaded Successful");
        }
        else
        {
            this.pushError(model.getSessionId(), "Session Not Exists");
        }
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
    public void pushUpdatedRegister(String sessionId, Map<String, String> data)
    {
        if (CsiracApplication.sessionExists(sessionId))
        {
            // TODO::Delete test code:
//            for (int i = 0; i < 20; i++)
//            {
//                data.put(i + "", i * i + "");
//            }

            String[] result = new String[data.size()];
            int i = 0;
            for (String key : data.keySet())
            {
                result[i] = "" + key + ":" + data.get(key);
                i++;
            }

            setResults(result, "register/" + sessionId);
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
    public void pushPcRegister(String sessionId, String pcRegister)
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
     * @param sessionId
     *         the session id
     * @param output
     *         output
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

    private String generateFakeResult(int length)
    {
        String result = "";
        for (int j = 0; j < length; j++) {
            int rand = (int) (Math.random() * 10);
            result += rand % 2;
        }
        return result;
    }
}
