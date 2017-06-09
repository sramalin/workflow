package workflow.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by sramalin on 29/05/17.
 */
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String name;

    private String description;


    private String priority;

    private String status;

    private long assignedTo;

    public long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public enum TicketStatus {

        NEW, ASSIGNED, COMPLETED, CLOSED,REOPENED
    };


    public String getStatus() {
        return status;
    }

    public void setStatus(TicketStatus ticketStatus) {
        this.status = ticketStatus.toString();
    }

    public Ticket() {
    }


    public Ticket replace(Ticket ticket) {

        name = getNotNullParam(ticket.getName(), name);
        description = getNotNullParam(ticket.getDescription(), description);
        priority = getNotNullParam(ticket.getPriority(), priority);
        return this;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public enum TicketPriority {

        HIGH, MEDIUM, LOW
    };

    public void setPriority(TicketPriority ticketPriority) {
        this.priority = ticketPriority.toString();
    }

    public String getPriority() {
        return priority;
    }

    private <T> T getNotNullParam(T first, T second) {
        if (first != null) {
            return first;
        }
        return second;

    }
}
