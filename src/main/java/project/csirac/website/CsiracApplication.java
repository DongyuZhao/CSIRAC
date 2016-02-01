package project.csirac.website;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.csirac.core.CsiracBootstrap;
import project.emulator.framework.api.debugger.Debugger;
import project.emulator.framework.api.debugger.IDebugger;
import project.emulator.framework.api.monitor.IMonitor;
import project.emulator.framework.api.monitor.Monitor;

@SpringBootApplication
public class CsiracApplication {

    /**
     * The holder that contains the wait time span of each emulator
     */
    public final static Map<String, Long> sessionLifeRecord = new HashMap<>();

    /**
     * The monitor
     */
    public final static IMonitor monitor = new Monitor();

    /**
     * The debugger
     */
    public final static IDebugger debugger = new Debugger();

    /**
     * Add new session into the system
     *
     * @param id the session id
     */
    public static void addSession(String id) {
        Long currentTime = (new Date()).getTime();
        sessionLifeRecord.put(id, currentTime);
        CsiracBootstrap.createEmulator(id, monitor, debugger);
    }

    /**
     * remove the session from the system
     *
     * @param id the session id
     */
    public static void removeSession(String id) {
        monitor.removeSession(id);
        debugger.removeSession(id);
        sessionLifeRecord.remove(id);
    }

    /**
     * check if the session exists
     *
     * @param sessionId the id of the session
     * @return if the session exists
     */
    public static boolean sessionExists(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return false;
        }
        return sessionLifeRecord.keySet().contains(sessionId);
    }

    /**
     * get the wait time span of the session
     *
     * @param id the id the session
     * @return the wait time span of the session
     */
    public static Long getSessionWaitSpan(String id) {
        if (sessionExists(id)) {
            Long current = (new Date()).getTime();
            Long start = sessionLifeRecord.get(id);
            return current - start;
        } else {
            return Long.MAX_VALUE;
        }
    }

    /**
     * update the wait time span of the session
     *
     * @param id the id of the session
     */
    public static void updateSessionLife(String id) {
        if (sessionExists((id))) {
            Long currentTime = (new Date()).getTime();
            sessionLifeRecord.put(id, currentTime);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CsiracApplication.class, args);
        CsiracBootstrap.setCsiracConfig();
        CsiracBootstrap.registerCsiracSymbolTable();
    }
}
