package workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sramalin on 31/05/17.
 */
@Controller
public class UserUIController {

    @RequestMapping(value = "/web/user/view", method = RequestMethod.GET)
    public String viewusers() {

        return "viewusers";

    }

    @RequestMapping(value = "/web/user/bulkupload", method = RequestMethod.GET)
    public String uploadUsers() {

        return "bulkuploadusers";

    }

}
