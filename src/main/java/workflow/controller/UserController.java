package workflow.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workflow.domain.Ticket;
import workflow.domain.User;
import workflow.service.UserService;

import java.io.IOException;
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

    @RequestMapping(value = "/user/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

        }

        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        userService.bulkUpload(bytes);

        return new ResponseEntity("Upload successful", HttpStatus.ACCEPTED);

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
