package workflow.controller;


import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workflow.service.TicketService;
import workflow.domain.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

        String fileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName);

        if (!fileExtension.equalsIgnoreCase("csv") ) {
            return new ResponseEntity("Only CSV files are allowed", HttpStatus.PRECONDITION_FAILED);

        }
        if (file.isEmpty()) {
            return new ResponseEntity("No file selected..", HttpStatus.PRECONDITION_FAILED);

        }

        byte[] bytes = file.getBytes();

        if(ticketService.bulkUpload(bytes))
            return new ResponseEntity("Upload successful", HttpStatus.ACCEPTED);
        else
            return new ResponseEntity("Upload failed. Please check the logs for further information", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @RequestMapping(value = "/ticket", method = RequestMethod.GET)
    public List<Ticket> getTicketsByName(@RequestParam(value = "name") String varName) {

        return ticketService.getTicketsByName(varName);

    }

    @RequestMapping(value = "/ticket/getnextticket", method = RequestMethod.GET)
    public List<Ticket> getNextAssignableTicket() {

        List<Ticket> ticketList = Arrays.asList(ticketService.getNextAssignableTicket());
        return ticketList;

    }

    @RequestMapping(value = "/ticket/bystatus", method = RequestMethod.GET)
    public List<Ticket> getTicketsByStatus(@RequestParam(value = "status") String varStatus) {

        return ticketService.getTicketsByStatus(varStatus);

    }
    @RequestMapping(value = "/ticket/byassignedto", method = RequestMethod.GET)
    public List<Ticket> getTicketsByAssignedTo(@RequestParam(value = "userName") String userName) {


        return ticketService.getTicketsByAssignedTo(userName);

    }

    @RequestMapping(value = "/tickets", method = RequestMethod.GET)
    public List<Ticket> getAllTickets() {

        return ticketService.getAllTickets();

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

    @RequestMapping(value = "/ticket/complete", method = RequestMethod.PUT)
    public ResponseEntity completeTicketByID(@RequestParam Long TicketId) {

        ticketService.setTicketCompletion(TicketId);
        return new ResponseEntity(HttpStatus.OK);

    }

}
