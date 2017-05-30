package workflow.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workflow.service.TicketService;
import workflow.domain.Ticket;

import java.util.List;

/**
 * Created by sramalin on 29/05/17.
 */
@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/ticket/create", method = RequestMethod.POST)
    public ResponseEntity ticketDetails(@RequestBody Ticket tkt) {

        ticketService.save(tkt);
        System.out.println("param name: "+ tkt.getName());
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/ticket/{name}", method = RequestMethod.GET)
    public List<Ticket> getTicketsByName(@PathVariable(value = "name") String varName) {

        return ticketService.getTicketsByName(varName);

    }

    @RequestMapping(value = "/ticket/update", method = RequestMethod.PUT)
    public ResponseEntity updateTicketByID(@RequestBody Ticket tkt) {

        ticketService.updateTicket(tkt);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/ticket/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteTicketByID(@RequestBody Ticket tkt) {

        ticketService.deleteTicket(tkt);
        return new ResponseEntity(HttpStatus.OK);

    }


}
