package project.csirac.website.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.csirac.website.models.document.DocumentDao;
import project.csirac.website.models.document.DocumentAbout;


/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String welcome(@RequestParam(name = "name", required = false, defaultValue = "there") String name, Model model) {
        model.addAttribute("name", name);
        return "welcome";
    }

    @RequestMapping("/help")
    public String help(Model model) {
        DocumentDao dao = new DocumentDao();
        model.addAttribute("document", dao.findByName("Help"));
        return "help";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        DocumentAbout About = new DocumentAbout();
        model.addAttribute("document", About.findByName("About"));
        return "about";
    }
}
