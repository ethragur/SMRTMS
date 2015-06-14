import android.content.Context;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import client.smrtms.com.smrtms_client.controller.GPSTracker;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.controller.LoginUser;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;
import client.smrtms.com.smrtms_client.tokens.FriendReqToken;

/**
 * Created by effi on 6/9/15.
 */
public class ServerControlTest extends TestCase
{
    ServerControl testController;

    @Mock
    Context t;

    @Mock
    GPSTracker gpsTracker;

   @Test
   public void testMethodExecutions()
   {
       try {
           t = Mockito.mock(Context.class);
           gpsTracker = Mockito.mock(GPSTracker.class);
           LoginUser.createInstance("test@test.com", "", t);

       }catch(Throwable e)
       {
           assertEquals(NullPointerException.class, e.getClass());
           //do nothing, catching first one because of Android init
       }
       LoginUser.getInstance().serverTask.setGpsTracker(gpsTracker);
       AuthenticationToken testAToken = new AuthenticationToken("test@test.com", "test");
       testAToken.access = true;
       JSONParser<AuthenticationToken> testParser = new JSONParser<>();
       String testInput = testParser.JSONWriter(testAToken);

       assertTrue(testAToken.email.equals(testParser.readJson(testInput, AuthenticationToken.class).email));

       testController = new ServerControl(testInput, testAToken);

       Thread x = new Thread(testController);
       x.start();

       try {
           Thread.sleep(1000);
       } catch (Exception e)
       {
       }

       assertFalse(x.isAlive());

       assert(LoginUser.getInstance().isLogin());

   }
}
