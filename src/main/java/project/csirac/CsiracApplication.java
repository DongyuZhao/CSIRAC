package project.csirac;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import project.csirac.models.emulator.IMonitor;
import project.csirac.models.emulator.Monitor;

@SpringBootApplication
public class CsiracApplication {

	public static IMonitor monitor = new Monitor();

	public static Map<String, Long> sessionList = new HashMap<>();

	public static boolean sessionExists(String sessionId) {
		if (StringUtils.isBlank(sessionId)) {
			return false;
		}
		return CsiracApplication.sessionList.keySet().contains(sessionId);
	}

	public static void main(String[] args) {
		SpringApplication.run(CsiracApplication.class, args);
	}
}
