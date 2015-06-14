import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import client.smrtms.com.smrtms_client.activity.LoginActivity;
import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.controller.ServerControl;
import client.smrtms.com.smrtms_client.tokens.AuthenticationToken;

/**
 * Created by effi on 4/20/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity>
{

    public LoginTest()
    {
        super(LoginActivity.class);
    }

    @Test
    public void testLogin()
    {
        AuthenticationToken testAToken = new AuthenticationToken("test@test.com", "test");
        testAToken.access = true;
        JSONParser<AuthenticationToken> testParser = new JSONParser<>();
        String testInput = testParser.JSONWriter(testAToken);

        assertTrue(testAToken.email.equals(testParser.readJson(testInput, AuthenticationToken.class).email));

        ServerControl testController = new ServerControl(testInput, testAToken);

        assertTrue(ServerControl.gotAuthToken);
    }

}
