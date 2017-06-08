package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.domain.Ticket;
import workflow.domain.TicketAssignment;
import workflow.domain.User;
import workflow.repository.TicketAssignmentRepository;
import workflow.repository.TicketRepository;
import workflow.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketAssignmentRepository ticketAssignmentRepository;

    public Boolean save(User user) {
        user.setUserId();
        user.setPassword();
        userRepository.save(user);
        return true;

    }

    public List<User> getUserbyUserId(String userId) {

        return userRepository.findByuserId(userId);
    }


    public Boolean updateUser(User user) {

        User persistentUser = userRepository.findOne(user.getNum());
        userRepository.save(persistentUser.replace(user));
        return true;
    }

    public Boolean deleteUser(User user) {

        userRepository.delete(user);
        return true;
    }


    public void assignTicket(String userId, long ticketID) {

        ticketAssignmentRepository.save(new TicketAssignment(ticketID, userId));

    }
}
