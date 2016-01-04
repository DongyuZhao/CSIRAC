package project.csirac.controllers.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

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
    public String greeting(String name) throws Exception
    {
        return  "Hello " + name + "!";
    }
}
