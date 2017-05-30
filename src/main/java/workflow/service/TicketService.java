package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.domain.Ticket;
import workflow.repository.TicketRepository;

import java.util.List;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Boolean save(Ticket tkt) {

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
}
