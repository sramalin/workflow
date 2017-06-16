package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workflow.domain.Ticket;
import workflow.domain.TicketAssignment;
import workflow.domain.User;
import workflow.repository.TicketAssignmentRepository;
import workflow.repository.TicketRepository;
import workflow.repository.UserRepository;
import workflow.utilities.CommonUtilities;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketAssignmentRepository ticketAssignmentRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CommonUtilities commonUtilities;

    public String save(User user) {
        String userID = generateUserID(user.getFirstName(),user.getLastName());
        user.setUserId(userID);
        user.setPassword();
        userRepository.save(user);
        return userID;

    }

    private String generateUserID(String firstName, String lastName) {


        String UserID = lastName.substring(0,1).trim()+firstName.trim();
        System.out.println("generated user id" + UserID);
        Boolean availability = false;
        String generatedUserID = UserID;
        int i = 0 ;
        do {

            if (i>0)  generatedUserID = UserID+i;
            if (getUserIDCount(generatedUserID) ==0 ) availability = true;
            else i++;

        } while(!availability);
        return generatedUserID;
    }

    private int getUserIDCount(String generatedUserID) {
        return userRepository.findByuserId(generatedUserID).size();
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


    public boolean assignTicket(long userId, long ticketID) {

        String userEmail = userRepository.findOne(userId).getEmail();
        String userLastname = userRepository.findOne(userId).getLastName();
        Ticket ticket = ticketRepository.findOne(ticketID);
        ticket.setStatus(Ticket.TicketStatus.ASSIGNED);
        ticket.setAssignedTo(userId);
        ticketRepository.save(ticket);
        ticketAssignmentRepository.save(new TicketAssignment(ticketID, userId));
        commonUtilities.sendMail(userEmail, userLastname, ticket.getId(),ticket.getName());
        return true;
    }

    public boolean bulkUpload(byte[] csvFile) {

        List<User> users = commonUtilities.loadObjectList(User.class, csvFile);
        for(User user:users) {
            String userID = generateUserID(user.getFirstName(), user.getLastName());
            user.setUserId(userID);
            user.setPassword();
            userRepository.save(user);
        }
        return true;

  }

    public List<User> getAllUsers() {
        Iterable<User> userIterable =  userRepository.findAll();
        List<User> userList= new ArrayList<>();
        for(User user:userIterable)
            userList.add(user);
        return userList;
    }
}
