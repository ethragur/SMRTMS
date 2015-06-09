import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;

/**
 * Created by effi on 6/9/15.
 */
public class ServerControlTest extends TestCase
{
    @Mock
    ServerControl testController;

   @Test
   public void testMethodExecutions()
   {

        testController = Mockito.mock(ServerControl.class);
        AuthenticationToken testAToken = new AuthenticationToken("test@test.com", "test");

        JSONReader<AuthenticationToken> testParser = new JSONReader<>();
        String testInput = testParser.JSONWriter(testAToken);
        assertEquals(testParser, testParser.readJson(testInput, AuthenticationToken.class));
        testController.input = testInput;
        testController.t = testAToken;


        Thread x = new Thread();
        x.start();



        //was method called
        Mockito.verify(testController).parseString();
        //check if thread is finished
        assertFalse(x.isAlive());



   }
}
