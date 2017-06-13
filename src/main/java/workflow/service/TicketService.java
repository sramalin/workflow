package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.domain.Ticket;
import workflow.repository.TicketRepository;
import workflow.utilities.CommonUtilities;

import java.io.File;
import java.util.List;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CommonUtilities commonUtilities;

    public Boolean save(Ticket tkt) {

        tkt.setStatus(Ticket.TicketStatus.NEW);
        ticketRepository.save(tkt);
        return true;

    }

    public List<Ticket> getTicketsByName(String varName) {

        return ticketRepository.findByName(varName);
    }

    public Boolean updateTicket(Ticket tkt) {

        Ticket persistentTicket = ticketRepository.findOne(tkt.getId());
        ticketRepository.save(persistentTicket.replace(tkt));
        return true;
    }

    public Boolean deleteTicket(Ticket tkt) {

        ticketRepository.delete(tkt);
        return true;
    }

    public boolean bulkUpload(byte[] csvFile) {

        List<Ticket> tickets = commonUtilities.loadObjectList(Ticket.class, csvFile);
        for(Ticket tkt:tickets) {
            tkt.setStatus(Ticket.TicketStatus.NEW);
            ticketRepository.save(tkt);
        }
        return true;


    }

}
