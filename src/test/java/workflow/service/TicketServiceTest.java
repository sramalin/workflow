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
import workflow.domain.Ticket;
import workflow.repository.TicketRepository;
import workflow.utilities.CommonUtilities;

import javax.validation.constraints.AssertTrue;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

/**
 * Created by sramalin on 13/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketServiceTest {


    @Mock
    private CommonUtilities commonUtilities;
    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketService ticketService;



    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getAllTickets() throws Exception {
        System.out.println(ticketService.getAllTickets());
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void getTicketsByName() throws Exception {

    }

    @Test
    public void updateTicket() throws Exception {

    }

    @Test
    public void deleteTicket() throws Exception {

    }

    @Test
    public void shouldReturnTrueWhenbulkUploadIsCalledWithNonEmptyFile() throws Exception {

        byte[] bytes = new byte[1];
        assertTrue(ticketService.bulkUpload(bytes));

    }
    @Test
    public void shouldReturnFalseWhenbulkUploadIsCalledWithEmptyFile() throws Exception {

        byte[] bytes = new byte[0];
        assertFalse(ticketService.bulkUpload(bytes));

    }

    @Test
    public void shouldreturnHighPriorityTicketalone() throws Exception{

        //Data setup
        Ticket tk1 = new Ticket();

        tk1.setPriority(Ticket.TicketPriority.HIGH);
        tk1.setStatus(Ticket.TicketStatus.NEW);
        tk1.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));

        Ticket tk2 = new Ticket();
        tk2.setPriority(Ticket.TicketPriority.LOW);
        tk2.setStatus(Ticket.TicketStatus.ASSIGNED);
        tk2.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));

        Ticket tk3 = new Ticket();
        tk3.setPriority(Ticket.TicketPriority.MEDIUM);
        tk3.setStatus(Ticket.TicketStatus.CLOSED);
        tk3.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));

        List<Ticket> ticketList = Arrays.asList(tk1,tk2,tk3);
        Ticket nextTicket = ticketService.getTicketBasedOnCriteria(ticketList);
        assertTrue(nextTicket.getPriority().equals("HIGH"));

    }

    @Test
    public void shouldreturnEarliestTicketalone() throws Exception{

        //Data setup
        Ticket tk1 = new Ticket();

        tk1.setPriority(Ticket.TicketPriority.HIGH);
        tk1.setStatus(Ticket.TicketStatus.NEW);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        tk1.setCreatedTimeStamp(timestamp);
        sleep(1000);

        Ticket tk2 = new Ticket();
        tk2.setPriority(Ticket.TicketPriority.HIGH);
        tk2.setStatus(Ticket.TicketStatus.NEW);
        tk2.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));

        sleep(1000);
        Ticket tk3 = new Ticket();
        tk3.setPriority(Ticket.TicketPriority.MEDIUM);
        tk3.setStatus(Ticket.TicketStatus.NEW);
        tk3.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));

        sleep(1000);
        List<Ticket> ticketList = Arrays.asList(tk1,tk2,tk3);


        Ticket nextTicket = ticketService.getTicketBasedOnCriteria(ticketList);
        assertTrue(nextTicket.getPriority().equals("HIGH"));
        assertTrue(nextTicket.getCreatedTimeStamp().equals(timestamp));

    }

}