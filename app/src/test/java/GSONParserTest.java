import junit.framework.TestCase;

import org.junit.Test;

import client.smrtms.com.smrtms_client.controller.JSONParser;
import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 5/4/15.
 */
public class GSONParserTest extends TestCase
{


    @Test
    public void testWriter()
    {
        Token n = new Token("token", "0");
        JSONParser<Token> tokenReader = new JSONParser<>();
        String t = tokenReader.JSONWriter(n);
        assertEquals(new String("{\"sTag\":\"token\",\"id\":\"0\"}"), t);
    }

    @Test
    public void testReader()
    {
        String t = new String("{\"sTag\":\"token\",\"id\":\"0\"}");
        JSONParser<Token> tokenReader = new JSONParser<>();
        Token w = new Token("token", "0");
        Token n = tokenReader.readJson(t, Token.class);
        assertTrue(w.sTag.equals(n.sTag));
    }
}
