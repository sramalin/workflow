package workflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import workflow.domain.Ticket;

import java.util.List;

/**
 * Created by sramalin on 31/05/17.
 */
@Controller
public class TicketUIController {

    @RequestMapping(value = "/web/ticket/view", method = RequestMethod.GET)
    public String viewtickets() {

        return "viewtickets";

    }
    @RequestMapping(value = "/web/ticket/search", method = RequestMethod.GET)
    public String getTicketsByID() {

        return "searchTicket";

    }

    @RequestMapping(value = "/web/ticket/bulkupload", method = RequestMethod.GET)
    public String uploadTickets() {

        return "bulkuploadtickets";

    }

    @RequestMapping(value = "/web/ticket/assignticket", method = RequestMethod.GET)
    public String assignTicket() {

        return "assignticket";

    }

}
