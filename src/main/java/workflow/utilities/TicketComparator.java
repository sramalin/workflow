package workflow.utilities;

import workflow.domain.Ticket;

import java.util.Comparator;

/**
 * Created by sramalin on 12/07/17.
 */
public class TicketComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket t1, Ticket t2) {
        if (t1.getCreatedTimeStamp().before( t2.getCreatedTimeStamp())) return -1;
        if (t1.getCreatedTimeStamp().after( t2.getCreatedTimeStamp())) return 1;
        return 0;
    }
}
