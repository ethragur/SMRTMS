package server;

import static jooqdb.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;


public class Main {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		String userName = "root";
		String password = "";
		String url = "jdbc:mysql://localhost:3306/library";
		
		// Connection is the only JDBC recource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try (Connection conn = DriverManager.getConnection(url, userName, password)) {
			DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
			Result<Record> result = create.select().from(AUTHOR).fetch();
			
			for (Record r : result) {
				Integer id = r.getValue(AUTHOR.ID);
				String firstName = r.getValue(AUTHOR.FIRST_NAME);
				String lastName = r.getValue(AUTHOR.LAST_NAME);
				
				System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
			}
		}
		
		// Simple exception handling
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
