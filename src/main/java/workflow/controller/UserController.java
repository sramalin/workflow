package workflow.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workflow.domain.Ticket;
import workflow.domain.User;
import workflow.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by sramalin on 29/05/17.
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity userDetails(@RequestBody User user) {

        String userID = userService.save(user);
        return new ResponseEntity("Successfully created user with userID: "+ userID,HttpStatus.OK);

    }

    @RequestMapping(value = "/user/byuserid", method = RequestMethod.GET)
    public List<User> getUserByUserID(@RequestParam(value = "userID") String varUserID) {

        return userService.getUserbyUserId(varUserID);

    }

    @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody User user) {

        userService.updateUser(user);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@RequestBody User user) {

        userService.deleteUser(user);
        return new ResponseEntity(HttpStatus.OK);

    }

    @RequestMapping(value = "/user/assignticket", method = RequestMethod.POST)
    public ResponseEntity assignTicketToUser(@RequestParam long userId, long ticketID) {


        userService.assignTicket(userId, ticketID);
        return new ResponseEntity(HttpStatus.ACCEPTED);

    }


}
