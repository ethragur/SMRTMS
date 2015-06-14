package tests;

import static org.junit.Assert.*;

import org.jooq.tools.json.JSONParser;
import org.junit.Test;

import server.tokens.Token;
import server.tokens.UserUpdateToken;
import server.ServerClasses.User;
import server.JSONReader;

public class JSONReaderTest {

	@Test
    public void testWriter()
    {
		Token n = new Token("token", "0");
        n.id = "0";
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
