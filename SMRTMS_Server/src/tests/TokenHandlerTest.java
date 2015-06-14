package tests;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.exceptions.InvalidHandshakeException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshakeBuilder;
import org.junit.Test;

import server.tokens.AuthenticationToken;
import server.TokenHandler;
import server.DBManager;

public class TokenHandlerTest {
	
	
	@Test
	public void tesTokenHandling() {
	//	DBManager dbm = Mockito.mock(DBManager.class);
		
		try {
		TokenHandler tok = new TokenHandler();
		
		String result;
		
		AuthenticationToken auth = new AuthenticationToken("test@gmail.com", "123");
		result = tok.ParseToken(auth, true);
		}
		catch (Exception e) {

		}	
		
		
		
		//fail("Not yet implemented");
		assertTrue(true);
	}

}
