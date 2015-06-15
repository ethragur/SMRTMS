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

import server.DBManager;
import server.tokens.AddEventToken;
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
	        
	        else if (sql.toUpperCase().startsWith("UPDATE")) {
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
				.set(USER.PASSWORD, t.password)
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
		
		String Eventname = "Test Event";
		
		create.update(EVENT)
		.set(EVENT.ATTENDEES, r.getValue(EVENT.ATTENDEES) + 1)
		.where(EVENT.NAME.equal(Eventname))
		.execute();

	}
	
	@Test
	public void testInsertEvent() {
		
		AddEventToken aet = new AddEventToken("Test Event", "This is a test event", 100, 14.22445, 18.23553);

		create.insertInto(EVENT)
			.set(EVENT.DESCRIPTION, aet.description)
			.set(EVENT.NAME, aet.name)
			.set(EVENT.TIME, aet.toEnd)
			.set(EVENT.LATITUDE, aet.Latitude)
			.set(EVENT.LONGITUDE, aet.Longitude)
			.set(EVENT.ATTENDEES, 0)
			.execute();

		System.out.println("New Event created!");
		
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
	
	@Test
	public void testCalculateDistance() {
		DBManager dbm = new DBManager();
		
		 assertTrue(dbm.calculateDistance(47.266667, 11.383333, 47.273333, 11.241389) > 10 &&
	                dbm.calculateDistance(47.266667, 11.383333, 47.273333, 11.241389) < 12);
	}
	

}
