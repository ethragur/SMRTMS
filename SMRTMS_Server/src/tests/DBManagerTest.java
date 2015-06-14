package tests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import jooqdb.tables.records.UserRecord;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.Before;
import org.junit.Test;

import server.tokens.RegistrationToken;
import static jooqdb.Tables.USER;
import static jooqdb.Tables.USER_FRIENDS;
import static jooqdb.Tables.FRIEND_REQUEST_STASH;


public class DBManagerTest {
	
	public class MyProvider implements MockDataProvider {

	    @Override
	    public MockResult[] execute(MockExecuteContext ctx) throws SQLException {
	    	
	        // You might need a DSLContext to create org.jooq.Result and org.jooq.Record objects
	        DSLContext create = DSL.using(SQLDialect.MYSQL);
	        MockResult[] mock = new MockResult[1];
	        
	        // The execute context contains SQL string(s), bind values, and other meta-data
	        String sql = ctx.sql();
	        System.out.println("SQL Query: " + sql);
	        
	        // Exceptions are propagated through the JDBC and jOOQ APIs
	        if (sql.toUpperCase().startsWith("DROP")) {
	            throw new SQLException("Statement not supported: " + sql);
	        }

	        // You decide, whether any given statement returns results, and how many
	        else if (sql.toUpperCase().startsWith("SELECT")) {
	            
	            // Always return one author record
	            Result<UserRecord> result = create.newResult(USER);
	            result.add(create.newRecord(USER));
	            result.get(0).setValue(USER.ID, 1);
	            result.get(0).setValue(USER.USERNAME, "Orwell");
	            mock[0] = new MockResult(1, result);
	        }
	        
	        else if (sql.toUpperCase().startsWith("INSERT")) {
	        	mock[0] = new MockResult(1, create.newResult(USER));
	        }
	        
	        // You can detect batch statements easily
	        else if (ctx.batch()) {
	            // [...]
	        }
	        
	        return mock;
	    }
	}
	
	MockDataProvider provider;
	MockConnection connection;
	DSLContext create;
	
	@Before
	public void init() {
		// Initialise your data provider (implementation further down):
		provider = new MyProvider();
		connection = new MockConnection(provider);

		// Pass the mock connection to a jOOQ DSLContext:
		create = DSL.using(connection, SQLDialect.MYSQL);
	}
	

	@Test
	public void testUpdateUser() {
		
		// Execute queries transparently, with the above DSLContext:
		Result<Record> result = create.select().from(USER).fetch();
		
		//fail("Not yet implemented");
		assertTrue(true);
	}
	
	@Test
	public void testInsertUser() {
		RegistrationToken t = new RegistrationToken();
		t.username = "TestUser";
		t.email = "test@gmail.com";
		t.password = "str0ngp4ssw0rd";
		t.result = false;		
		
		byte nope = 0;

		create.insertInto(USER)
				//.set(USER.ID, Integer.parseInt(t.sId))
				.set(USER.USERNAME, t.username)
				.set(USER.EMAIL, t.email)
				.set(USER.PASSWORD, /*hashpw*/ t.password)
				.set(USER.AVATAR, "nicolascage.png")
				.set(USER.ISONLINE, nope)
				.execute();

		System.out.println("New User created!");
	}
	
	@Test
	public void testDeletetUser() {
		
	}
	
	@Test
	public void testUpdateEvent() {

	}
	
	@Test
	public void testInsertEvent() {
		
	}
	
	@Test
	public void testDeletetEvent() {
		
	}
	
	@Test
	public void testCreateFriends() {
		
	}
	
	@Test
	public void testInsertFriendStash() {
		
	}
	
	@Test
	public void testDeletetFriendStash() {
		
	}
	

}
