package workflow.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import workflow.repository.TicketRepository;
import workflow.repository.UserRepository;
import workflow.utilities.CommonUtilities;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;


import java.io.File;
import java.nio.file.FileSystem;

import static org.junit.Assert.*;

/**
 * Created by sramalin on 19/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @Mock
    private CommonUtilities commonUtilities;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldReturnTrueWhenbulkUploadIsCalledWithNonEmptyFile() throws Exception {

        byte[] bytes = new byte[1];
        assertTrue(userService.bulkUpload(bytes));

    }


    @Test
    public void shouldReturnFalseWhenbulkUploadIsCalledWithEmptyFile() throws Exception {

        byte[] bytes = new byte[0];
        assertFalse(userService.bulkUpload(bytes));

    }

//TODO - To fix
    @Test
    public void shouldReturnTrueAfterSuccessfuleAssignment() throws Exception {

        String userName = "admin";
        String roleName = "ROLE_USER";
        assertTrue(userService.updateUserWithRole(userName,roleName));

    }

}