package project.csirac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.csirac.helper.StringHelper;
import project.csirac.models.emulator.IMonitor;
import project.csirac.models.emulator.Monitor;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CsiracApplication {

    public static IMonitor monitor = new Monitor();

	public static Map<String, Long> sessionList = new HashMap<>();

    public static  boolean sessionExists(String sessionId)
    {
        if (StringHelper.isNullOrWhiteSpace(sessionId))
        {
            return false;
        }
        return CsiracApplication.sessionList.keySet().contains(sessionId);
    }

	public static void main(String[] args) {
		SpringApplication.run(CsiracApplication.class, args);
	}
}
