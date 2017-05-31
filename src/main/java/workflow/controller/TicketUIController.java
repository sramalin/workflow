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

    @RequestMapping(value = "/web/search", method = RequestMethod.GET)
    public String getTicketsByID() {

        return "searchTicket";

    }

}
