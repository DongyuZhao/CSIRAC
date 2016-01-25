package project.csirac.website.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dy.Zhao on 2016/1/4 0004.
 */
@RestController
public class EmulatorApiController
{
    @RequestMapping(value = "/api/upload_program", method = RequestMethod.POST)
    public HttpStatus upload(@RequestParam(name = "program", required = true) String[] program)
    {
        if (program != null && program.length != 0)
        {

        }
        return HttpStatus.OK;
    }
}
