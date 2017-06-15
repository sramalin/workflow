package workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sramalin on 31/05/17.
 */
@Controller
public class UIController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage() {

        return "index";

    }

}
