package workflow.controller;


import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileSystemUtils;
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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {

        return userService.getAllUsers();

    }

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public ResponseEntity userDetails(@RequestBody User user) {

        String userID = userService.save(user);
        return new ResponseEntity("Successfully created user with userID: "+ userID,HttpStatus.OK);

    }

    @RequestMapping(value = "/user/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) throws IOException {

        String fileName = file.getOriginalFilename();
        String fileExtension = FilenameUtils.getExtension(fileName);
        if (!fileExtension.equalsIgnoreCase("csv") ) {
            return new ResponseEntity("Only CSV files are allowed", HttpStatus.PRECONDITION_FAILED);

        }

        if (file.isEmpty() ) {
            return new ResponseEntity("No file selected..", HttpStatus.PRECONDITION_FAILED);

        }

        byte[] bytes = file.getBytes();

        userService.bulkUpload(bytes);

        return new ResponseEntity("Upload successful", HttpStatus.ACCEPTED);

    }

    @RequestMapping(value = "/user/byuserid", method = RequestMethod.GET)
    public List<User> getUserByUserID(@RequestParam(value = "userID") String varUserID) {

        return userService.getUserbyUsername(varUserID);

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

        String displayMsg = "Assignment completed and mail has been sent to the assigned user";
        String errorMsg = "Assignment not completed. Please check system log";
        if(userService.assignTicket(userId, ticketID))

            return new ResponseEntity(displayMsg, HttpStatus.OK);
        else
            return new ResponseEntity(errorMsg,HttpStatus.OK);

    }


}
