import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import org.junit.Test;

import client.smrtms.com.smrtms_client.activity.MainScreen;
import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.User;

/**
 * Created by effi on 5/4/15.
 */
public class GSONReaderTest extends TestCase
{




    @Test
    public void testWriter()
    {
        /*User n = new User("TestUser", "0000", 0d, 0d);
        JSONReader<User> userJSONReader = new JSONReader<>();


        String t = userJSONReader.JSONWriter(n);
        assertEquals(new String("{\"Username\":\"TestUser\",\"ID\":\"0000\",\"Latitude\":0.0,\"Longitude\":0.0}"), t);*/
    }

    @Test
    public void testReader()
    {
       /* String t = new String("{\"Username\":\"TestUser\",\"ID\":\"0000\",\"Latitude\":0.0,\"Longitude\":0.0}");
        JSONReader<User> userJSONReader = new JSONReader<>();
        User w = new User("TestUser", "0000", 0d, 0d);
        User n = userJSONReader.readJson(t, User.class);

        assertEquals(w.toString(), n.toString());*/
    }
}
