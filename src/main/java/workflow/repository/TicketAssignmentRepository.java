package workflow.repository;

import org.springframework.data.repository.CrudRepository;
import workflow.domain.Ticket;
import workflow.domain.TicketAssignment;

import java.util.List;

/**
 * Created by sramalin on 30/05/17.
 */


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface TicketAssignmentRepository extends CrudRepository<TicketAssignment, Long> {

    List<TicketAssignment> findByuserID(String varName);

}

