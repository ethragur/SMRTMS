import junit.framework.TestCase;

import org.junit.Test;

import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.User;

/**
 * Created by effi on 5/4/15.
 */
public class GSONReaderTest extends TestCase
{

    @Test
    public void checkWriter()
    {
        User n = new User("TestUser", "0000", 0d, 0d);
        JSONReader<User> userJSONReader = new JSONReader<>();


        String t = userJSONReader.JSONWriter(n);


        assertEquals(new String("{\"Username:\"\"TestUser\",\"ID\n:\"0000\",\"Latitude\":\"0\",\"Longitude\":\"0\"}"), t);
    }
}
