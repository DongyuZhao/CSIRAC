package project.csirac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.csirac.models.emulator.IMonitor;
import project.csirac.models.emulator.Monitor;

@SpringBootApplication
public class CsiracApplication {

    public static IMonitor monitor = new Monitor();

	public static void main(String[] args) {
		SpringApplication.run(CsiracApplication.class, args);
	}
}
