package tests;

import static org.junit.Assert.*;

import org.jooq.tools.json.JSONParser;
import org.junit.Test;

import client.smrtms.com.smrtms_client.tokens.LogoutToken;
import client.smrtms.com.smrtms_client.tokens.Token;
import server.JSONReader;
import server.TokenHandler;

public class ServerTest {
	TokenHandler tokenhandler;
	
	@Test
	public void test() {
		LogoutToken logouttoken = new LogoutToken();
		
		JSONReader reader = new JSONReader<Token>();
		String answer = reader.JSONWriter( logouttoken );
		
		//WebSocket conn = new WebSocket();
		
		//tokenhandler = new TokenHandler(logouttoken, answer, conn);
		fail("Not yet implemented");
	}
}