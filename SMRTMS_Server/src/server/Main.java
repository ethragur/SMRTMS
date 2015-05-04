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

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONObject;

/*
 *  Prepare the SQL Server
 *  Step 1 - install mysql, user: "root", pw: "" (none)
 *  Step 2 - start the msysql deamon
 *  Step 3 - read the db_script.sql into the database, it will generate all the tables
 */


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/SMRTMS";
		
		// Connection is the only JDBC recource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName, password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Result<Record> result = create.select().from(USER).fetch();
			
			for (Record r : result) {
				Integer id = r.getValue(USER.ID);
				String firstName = r.getValue(USER.FIRST_NAME);
				String lastName = r.getValue(USER.LAST_NAME);
				
				System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
			}
		}
		
		// Simple exception handling
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Do the server stuff
		try {
			Server server = new Server(8887);
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

}
