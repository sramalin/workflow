package workflow.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workflow.domain.Ticket;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sramalin on 29/05/17.
 */
@RestController
public class TicketController {


    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/CreateTicket", method = RequestMethod.POST)
    public Ticket ticketDetails(@RequestParam(value="name", defaultValue="1" ) String name, @RequestParam(value="description", defaultValue="1" ) String description) {
        System.out.println("param name: "+ name);
        System.out.println("param description: "+ description);
        Ticket ticket = new  Ticket(counter.incrementAndGet(), name, description);
        System.out.println(ticket.getId());
        System.out.println(ticket.getName());
        System.out.println(ticket.getDescription());
        return ticket;
    }

    @RequestMapping(value = "/CreateTicketform", method = RequestMethod.POST)
    public ResponseEntity ticketDetails(@RequestBody Ticket tkt) {
        System.out.println("param name: "+ tkt.getName());
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

}
