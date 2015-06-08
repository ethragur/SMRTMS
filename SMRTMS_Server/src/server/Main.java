package server;

import static jooqdb.Tables.*;
import static org.jooq.impl.DSL.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.zip.GZIPInputStream;

import org.java_websocket.WebSocketImpl;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONObject;

/*
 *  Prepare the SQL Server
 *  Step 1 - install mysql
 *  Step 2 - start the msysql deamon
 *  Step 3 - read the db_script.sql into the database, it will generate all the tables
 */


public class Main {

	public static void main(String[] args) {
		System.out.println("Starting Clubbr Server... please stand by");
	
		boolean serverrunning = true;
		
		while (serverrunning) {
			serverrunning = startServer();
		}
		
	}
	
	private static boolean startServer() {
		// Do the server stuff
				WebSocketImpl.DEBUG = true;
				
				try {
					Server server = new Server(8887);
					server.start();
			        System.out.println( "Clubbr v0.3 Server started on port: " + server.getPort() );
			        TokenHandler tokenhandlerinit = new TokenHandler();
			        System.out.println( "Ready!" );
			        
			        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
			        while ( true ) {
			            String in = sysin.readLine();
			            //server.sendToAll( in );
			            if( in.equals( "exit" ) ) {
			                server.stop();
			                return false;
			            } else if( in.equals( "restart" ) ) {
			                server.stop();
			                server.start();
			                return true;
			            } 
			        }
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				return true;
	}

}
