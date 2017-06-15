package workflow.repository;

import org.springframework.data.repository.CrudRepository;
import workflow.domain.Ticket;

import java.util.List;

/**
 * Created by sramalin on 30/05/17.
 */


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findByName(String varName);

    List<Ticket> findBystatus(String varStatus);
}

