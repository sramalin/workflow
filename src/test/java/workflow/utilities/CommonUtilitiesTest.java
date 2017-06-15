package workflow.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sramalin on 15/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonUtilitiesTest {
    @InjectMocks
    CommonUtilities commonUtilities;

    @Test
    public void sendMail() throws Exception {

        assertTrue(commonUtilities.sendMail("test@test.com","userLastname1", 1234,"file1"));

    }

}