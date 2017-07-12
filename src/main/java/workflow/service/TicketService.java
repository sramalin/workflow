package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.domain.Ticket;
import workflow.domain.User;
import workflow.repository.TicketRepository;
import workflow.repository.UserRepository;
import workflow.utilities.CommonUtilities;
import workflow.utilities.TicketComparator;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonUtilities commonUtilities;

    public Boolean save(Ticket tkt) {

        tkt.setStatus(Ticket.TicketStatus.NEW);
        Timestamp timestamp =  new Timestamp(System.currentTimeMillis());
        tkt.setCreatedTimeStamp(timestamp);
        System.out.println("### Ticket creation time ###" + timestamp);

        ticketRepository.save(tkt);
        return true;

    }

    public List<Ticket> getTicketsByName(String varName) {

        return ticketRepository.findByName(varName);
    }

    public List<Ticket> getTicketsByStatus(String varStatus) {

        return ticketRepository.findBystatus(varStatus);
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

        if(csvFile.length ==0)
            return false;
        List<Ticket> tickets = commonUtilities.loadObjectList(Ticket.class, csvFile);
        for(Ticket tkt:tickets) {
            tkt.setStatus(Ticket.TicketStatus.NEW);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //java.sql.Date currentTimestamp = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            tkt.setCreatedTimeStamp(timestamp);
            System.out.println("### Ticket creation time" + timestamp);
            ticketRepository.save(tkt);
        }
        return true;

    }

    public List<Ticket> getAllTickets() {
        Iterable<Ticket> ticketIterable =  ticketRepository.findAll();
        List<Ticket> ticketList= new ArrayList<>();
        for(Ticket ticket:ticketIterable)
            ticketList.add(ticket);
        return ticketList;
    }

    public Ticket getNextAssignableTicket() {
        //Return the first element of earliest created ticket with new status
        List<Ticket> newStatusticketList = ticketRepository.findBystatus("New");
        return getTicketBasedOnCriteria(newStatusticketList);
    }

    public Ticket getTicketBasedOnCriteria(List<Ticket> newStatusticketList) {
        newStatusticketList.sort(new TicketComparator());

        List<Ticket> highPriorityList = newStatusticketList.stream()                // convert list to stream
                .filter(x -> "HIGH".equals(x.getPriority()))
                .collect(Collectors.toList());

        List<Ticket> lowPriorityList = newStatusticketList.stream()                // convert list to stream
                .filter(x -> "LOW".equals(x.getPriority()))
                .collect(Collectors.toList());

        List<Ticket> mediumPriorityList = newStatusticketList.stream()                // convert list to stream
                .filter(x -> "MEDIUM".equals(x.getPriority()))
                .collect(Collectors.toList());

        if (highPriorityList.size()!=0)
            return highPriorityList.get(0);
        if (mediumPriorityList.size()!=0)
            return mediumPriorityList.get(0);
        if (lowPriorityList.size()!=0)
            return lowPriorityList.get(0);

        return null;
    }

    public List<Ticket> getTicketsByAssignedTo(String userName) {

        List<User> user = userRepository.findByusername(userName);
        return ticketRepository.findByassignedTo(user.get(0).getNum());
    }
}
