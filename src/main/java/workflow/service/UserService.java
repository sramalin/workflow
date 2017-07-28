package workflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import workflow.domain.Authority;
import workflow.domain.Ticket;
import workflow.domain.TicketAssignment;
import workflow.domain.User;
import workflow.repository.AuthorityRepository;
import workflow.repository.TicketAssignmentRepository;
import workflow.repository.TicketRepository;
import workflow.repository.UserRepository;
import workflow.utilities.CommonUtilities;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sramalin on 30/05/17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private TicketAssignmentRepository ticketAssignmentRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CommonUtilities commonUtilities;

    public String save(User user) {
        String userID = generateUserID(user.getFirstName(),user.getLastName());
        user.setUsername(userID);
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
        return userRepository.findByusername(generatedUserID).size();
    }


    public List<User> getUserbyUsername(String userId) {

        return userRepository.findByusername(userId);
    }


    public Boolean updateUser(User user) {

        User persistentUser = userRepository.findOne(user.getUsername());
        userRepository.save(persistentUser.replace(user));
        return true;
    }

    public Boolean deleteUser(User user) {

        userRepository.delete(user);
        return true;
    }


    public String assignTicket(String userName, long ticketID) {

        String displayMsg = "Assignment completed and mail has been sent to the assigned user";
        String errorMsg = "Assignment not completed. Please check system log";
        try {
            String userEmail = userRepository.findOne(userName).getEmail();
            String userLastname = userRepository.findOne(userName).getLastName();
            if (checkExistingAssignedTicket(userName))
                return "Assignment Failed. You have got one or more tickets in Assigned State";

            Ticket ticket = ticketRepository.findOne(ticketID);

            ticket.setStatus(Ticket.TicketStatus.ASSIGNED);
            ticket.setAssignedTo(userName);
            ticketRepository.save(ticket);
            ticketAssignmentRepository.save(new TicketAssignment(ticketID, userName));
            commonUtilities.sendMail(userEmail, userLastname, ticket.getId(), ticket.getName());
            return displayMsg;
        }
        catch (Exception e){
            return errorMsg + e.getMessage();
        }


    }

    private boolean checkExistingAssignedTicket(String userName) {
        List<TicketAssignment> existingAssignedTickets = ticketAssignmentRepository.findByuserID(userName);
        for (TicketAssignment ticketAssignment:existingAssignedTickets){
            Ticket existingTicket = ticketRepository.findOne(ticketAssignment.getTicketID());
            if(existingTicket.getStatus().equals(Ticket.TicketStatus.ASSIGNED))
                return true;
        }
        return false;
    }

    public boolean bulkUpload(byte[] csvFile) {

        User createdUser;
        if(csvFile.length ==0)
            return false;
        List<User> users = commonUtilities.loadObjectList(User.class, csvFile);
        for(User user:users) {
            String userID = generateUserID(user.getFirstName(), user.getLastName());
            user.setUsername(userID);
            user.setPassword();
            userRepository.save(user);

        }


        return true;

  }

    public boolean UserBulkUpload(byte[] csvFile) throws ParseException {


        User createdUser;
        User newUser = new User();
        if(csvFile.length ==0)
            return false;
        List<String[]> csvRows= commonUtilities.loadManyToManyRelationship(csvFile);
        for(String[] row:csvRows) {
            String username = generateUserID(row[0].toString(), row[1].toString());
            String firstName = row[0].toString();
            String lastName = row[1].toString();
            String dob = row[2].toString();
            DateFormat df = new SimpleDateFormat("yyyy-dd-MM");
            Date dobConverted = df.parse(dob);
            String activationStatus = row[3].toString();
            String email = row[4].toString();
            String roleName = row[5].toString();
            System.out.println("Read roles"+ roleName);

            newUser.setUsername(username);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
            newUser.setPassword();
            newUser.setActivationStatus(Boolean.parseBoolean(activationStatus));
            newUser.setDob(dobConverted);
            newUser.setEmail(email);


            createdUser =userRepository.save(newUser);
            System.out.println(createdUser.getFirstName() + " user successfully created");

            for(String roleitem:roleName.split("-"))
                System.out.println("Assignment status:  "+updateUserWithRole(createdUser.getUsername(),roleitem));


        }


        return true;

    }
    public boolean bulkRoleAssignment(byte[] csvFile) {


        if(csvFile.length ==0)
            return false;
        List<String[]> csvRows = commonUtilities.loadManyToManyRelationship(csvFile);
        for(String[] row:csvRows) {
            String username = row[0].toString();
            String rolename = row[1].toString();
            System.out.println("### csv user"+ username);
            System.out.println("### csv rolename"+ rolename);

            System.out.println("Assignment status : "+updateUserWithRole(username, rolename));

        }


        return true;

    }
    @Transactional

    public boolean updateUserWithRole(String username, String rolename) {

        Set<Authority> authset = new HashSet<>();

        List<User> listofUsers = userRepository.findByusername(username);
        Authority authority  = authorityRepository.findOne(rolename);

        authset.add(authority);


        User currentUser = userRepository.findOne(username.trim());


        authset.add(authority);
        authset.addAll(currentUser.getAuthorities());

        currentUser.setAuthorities(authset);

        userRepository.save(currentUser);

        return true;
    }

    public List<User> getAllUsers() {
        Iterable<User> userIterable =  userRepository.findAll();
        List<User> userList= new ArrayList<>();
        for(User user:userIterable)
            userList.add(user);
        return userList;
    }

    public String assignTicketToLoggedInUser(long ticketID) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); //get logged in username

        System.out.println("Assigning "+ticketID +" to user "+username);
        return assignTicket(username,ticketID);

    }
}
