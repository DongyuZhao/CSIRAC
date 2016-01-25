package project.csirac.website;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.csirac.core.Bootstrap;
import project.emulator.framework.api.debugger.Debugger;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.monitor.Monitor;

@SpringBootApplication
public class CsiracApplication
{

    //public static IMonitor monitor = new Monitor();

    public static Map<String, Long> sessionLifeRecord = new HashMap<>();

    public static IMonitor monitor = new Monitor();

    public static IDebugger debugger = new Debugger();

    public static void addSession(String id)
    {
        Long currentTime = (new Date()).getTime();
        sessionLifeRecord.put(id, currentTime);
        Bootstrap.createEmulator(id, monitor, debugger);
    }

    public static void removeSession(String id)
    {
        monitor.removeSession(id);
        debugger.removeSession(id);
        sessionLifeRecord.remove(id);
    }

    public static boolean sessionExists(String sessionId)
    {
        if (StringUtils.isBlank(sessionId))
        {
            return false;
        }
        return sessionLifeRecord.keySet().contains(sessionId);
    }

    public static Long getSessionWaitSpan(String id)
    {
        if (sessionExists(id))
        {
            Long current = (new Date()).getTime();
            Long start = sessionLifeRecord.get(id);
            return current - start;
        }
        else
        {
            return Long.MAX_VALUE;
        }
    }

    public static void updateSessionLife(String id)
    {
        if (sessionExists((id)))
        {
            Long currentTime = (new Date()).getTime();
            sessionLifeRecord.put(id, currentTime);
        }
    }

    public static void main(String[] args)
    {
        SpringApplication.run(CsiracApplication.class, args);
        Bootstrap.registerCsiracSymbolTable();
    }
}
