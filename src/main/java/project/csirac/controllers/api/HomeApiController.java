package project.csirac.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.csirac.models.document.DocumentDao;
import project.csirac.models.document.entities.Document;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */
@RestController
public class HomeApiController
{
    @RequestMapping(value = "/api/help", method = RequestMethod.GET)
    public Document help(@RequestParam(name = "component_name", required = true) String componentName)
    {
        DocumentDao dao = new DocumentDao();
        return dao.findByName(componentName);
    }
}