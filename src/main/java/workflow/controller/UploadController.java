package workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import workflow.service.TicketService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class UploadController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/user/uploadsample", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity singleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");

        }

        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        ticketService.bulkUpload(bytes);

        return new ResponseEntity("Upload successful", HttpStatus.ACCEPTED);

    }
}