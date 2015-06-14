package tests;

import static org.junit.Assert.*;

import org.jooq.tools.json.JSONParser;
import org.junit.Test;

import client.smrtms.com.smrtms_client.tokens.Token;
import client.smrtms.com.smrtms_client.tokens.UserUpdateToken;
import ServerClasses.User;
import server.JSONReader;

public class JSONReaderTest {

	@Test
    public void testWriter()
    {
		Token n = new Token("token", "0");
		JSONReader<Token> tokenReader = new JSONReader<Token>();
        String t = tokenReader.JSONWriter(n);
        assertEquals(new String("{\"sTag\":\"token\",\"id\":\"0\"}"), t);
    }

    @Test
    public void testReader()
    {
    	String t = new String("{\"sTag\":\"token\",\"id\":\"0\"}");
    	JSONReader<Token> tokenReader = new JSONReader<Token>();
        Token w = new Token("token", "0");
        Token n = tokenReader.readJson(t, Token.class);
        assertTrue(w.sTag.equals(n.sTag));
    }

}
