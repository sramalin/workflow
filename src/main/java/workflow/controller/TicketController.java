package workflow.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workflow.service.TicketService;
import workflow.domain.Ticket;

import java.io.IOException;
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

    @RequestMapping(value = "/ticket/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

        }

        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        ticketService.bulkUpload(bytes);

        return new ResponseEntity("Upload successful", HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public List<Ticket> getTicketsByName(@RequestParam(value = "name") String varName) {

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
