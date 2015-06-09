import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import client.smrtms.com.smrtms_client.controller.JSONParser;
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

        JSONParser<AuthenticationToken> testParser = new JSONParser<>();
        String testInput = testParser.JSONWriter(testAToken);
        assertTrue(testAToken.email.equals(testParser.readJson(testInput, AuthenticationToken.class).email));
        testController.input = testInput;
        testController.t = testAToken;


        Thread x = new Thread(testController);
        x.start();



        //was method called Thread called
        Mockito.verify(testController, Mockito.times(1)).run();
        //check if thread is finished
        assertFalse(x.isAlive());



   }
}
