package workflow.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by sramalin on 29/05/17.
 */
@Entity
public class TicketAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long ticketID;

    private long userID;


    public TicketAssignment() {
    }

    public TicketAssignment(long ticketID, long userID) {
        this.ticketID = ticketID;
        this.userID = userID;
    }

    public TicketAssignment replace(TicketAssignment ticketAssignment) {

        ticketID = getNotNullParam(ticketAssignment.getTicketID(), ticketID);
        userID = getNotNullParam(ticketAssignment.getUserID(), userID);
        return this;
    }

    public long getTicketID() {
        return ticketID;
    }

    public long getUserID() {
        return userID;
    }


    private <T> T getNotNullParam(T first, T second) {
        if (first != null) {
            return first;
        }
        return second;

    }
}
