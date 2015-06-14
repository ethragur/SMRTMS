import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import client.smrtms.com.smrtms_client.activity.LoginActivity;

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

    }

}
