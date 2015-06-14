package tests;

import static org.junit.Assert.*;

import org.mockito.Mock;
import org.mockito.Mockito;

import java.lang.Exception;
import java.lang.Thread;
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
import server.JSONReader;
import server.TokenHandler;
import server.DBManager;

public class TokenHandlerTest {
	
	@Mock
	WebSocket connection;
	@Test
	public void tesTokenHandling() {
		
		try {
			connection = Mockito.mock(WebSocket.class);

			String result;

			AuthenticationToken auth = new AuthenticationToken("test@gmail.com", "123");
			JSONReader<AuthenticationToken> tokenReader = new JSONReader<AuthenticationToken>();
			String t = tokenReader.JSONWriter(auth);
			TokenHandler tok = new TokenHandler(auth, t, connection);
			Thread x = new Thread(tok);
			x.start();
			assertTrue(x.isAlive());
			Thread.sleep(1000);
			assertFalse(x.isAlive());

		}
		catch (Exception e) {

		}	
	}



}
