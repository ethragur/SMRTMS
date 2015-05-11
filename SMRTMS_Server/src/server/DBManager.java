package server;

import static jooqdb.Tables.USER;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DBManager {
	String userName = "root";
	String password = ""; //"sepmLoot";
	String url = "jdbc:mysql://localhost:3306/SMRTMS";
	
	public void bla () {
		// Connection is the only JDBC recource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		DSLContext create = getConnection();
			
		Result<Record> result = create.select().from(USER).fetch();
		
		for (Record r : result) {
			Integer id = r.getValue(USER.ID);
			String firstName = r.getValue(USER.FIRST_NAME);
			String lastName = r.getValue(USER.LAST_NAME);
			
			System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
		}

	}
	
	private DSLContext getConnection() {
		try {
			Connection conn = DriverManager.getConnection(url, userName, password);
			return DSL.using(conn, SQLDialect.MYSQL);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}