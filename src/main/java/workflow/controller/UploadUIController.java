package workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by sramalin on 10/06/17.
 */
@Controller
public class UploadUIController {

    @RequestMapping(value = "/web/user/bulkuploadtest", method = RequestMethod.GET)
    public String getTicketsByIDtest() {

        return "bulkUpload";

    }

}
