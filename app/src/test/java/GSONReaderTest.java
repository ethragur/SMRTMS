import android.test.ActivityInstrumentationTestCase2;

import junit.framework.TestCase;

import org.junit.Test;

import client.smrtms.com.smrtms_client.controller.JSONReader;
import client.smrtms.com.smrtms_client.controller.User;
import client.smrtms.com.smrtms_client.tokens.Token;

/**
 * Created by effi on 5/4/15.
 */
public class GSONReaderTest extends TestCase
{


    @Test
    public void testWriter()
    {
        Token n = new Token("token", "0");
        JSONReader<Token> tokenReader = new JSONReader<>();
        String t = tokenReader.JSONWriter(n);
        assertEquals(new String("{\"sTag\":\"token\",\"id\":\"0\"}"), t);
    }

    @Test
    public void testReader()
    {

        String t = new String("{\"sTag\":\"token\",\"id\":\"0\"}");
        JSONReader<Token> tokenReader = new JSONReader<>();
        Token w = new Token("token", "0");
        Token n = tokenReader.readJson(t, Token.class);

        assertEquals(w.toString(), n.toString());
    }
}
