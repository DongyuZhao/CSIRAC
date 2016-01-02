package project.csirac.controller.socket;

import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import project.csirac.models.document.entities.Document;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */

@Controller
public class HomeController
{
    private SimpMessagingTemplate template;

    @Autowired
    public HomeController(SimpMessagingTemplate template)
    {
        this.template = template;
    }

    void setResults(String ret, String destination)
    {
        template.convertAndSend("/topic/" + destination, ret);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public void greeting(String name) throws  Exception {
        for (int i = 0; i < 100000; i++)
        {
            setResults("" + i, "greetings");
        }
    }
}
