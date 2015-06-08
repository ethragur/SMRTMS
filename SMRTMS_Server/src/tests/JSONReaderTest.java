package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;
import ServerClasses.User;
import server.JSONReader;

public class JSONReaderTest {

	@Test
    public void testWriter()
    {
        User n = new User("TestUser", "0000", 0d, 0d);
        JSONReader<UserUpdateToken> userJSONReader = new JSONReader<>();


        //String t = userJSONReader.JSONWriter(n);
        //assertEquals(new String("{\"Username\":\"TestUser\",\"ID\":\"0000\",\"Latitude\":0.0,\"Longitude\":0.0}"), t);
    }

    @Test
    public void testReader()
    {
        String t = new String("{\"Username\":\"TestUser\",\"ID\":\"0000\",\"Latitude\":0.0,\"Longitude\":0.0}");
        JSONReader<UserUpdateToken> userJSONReader = new JSONReader<>();
        User w = new User("TestUser", "0000", 0d, 0d);
        //User n = userJSONReader.readJson(t, User.class);

        //assertEquals(w.toString(), n.toString());
    }

}
