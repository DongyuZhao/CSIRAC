package project.csirac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import project.csirac.website.CsiracApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CsiracApplication.class)
@WebAppConfiguration
public class CsiracApplicationTests {

	@Test
	public void contextLoads() {
	}

}
