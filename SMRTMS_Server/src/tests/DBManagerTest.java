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
import server.tokens.UserUpdateToken;
import static jooqdb.Tables.USER;
import static jooqdb.Tables.USER_FRIENDS;
import static jooqdb.Tables.FRIEND_REQUEST_STASH;
import static jooqdb.Tables.EVENT;


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
	        
	        else if (sql.toUpperCase().startsWith("DELETE")) {
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
		
		UserUpdateToken t = new UserUpdateToken(16.23534, 11.23454);
		t.id = "1";
		
		create.update(USER)
			.set(USER.LONGITUDE, t.Longitude)
			.set(USER.LATITUDE, t.Latitude)
			.where(USER.ID.equal(Integer.parseInt(t.id)))
			.execute();
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
	public void testUpdateEvent() {
		
		String Eventname = "Test Event";
		
		create.update(EVENT)
		.set(EVENT.ATTENDEES, 2)
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
		String Eventname = "Test Event";
		
		create.delete(EVENT)
			.where(EVENT.NAME.equal(Eventname))
			.execute();
	}
	
	@Test
	public void testCreateFriends() {
		
		int friender_ID = 78;
		int friendee_ID = 87;
		
		byte nope = 0;
		
		create.insertInto(USER_FRIENDS)
			.set(USER_FRIENDS.FRIENDER_ID, friender_ID)
			.set(USER_FRIENDS.FRIENDEE_ID, friendee_ID)
			.set(USER_FRIENDS.TRACKING_FLAG, nope)
			.execute();
	}
	
	@Test
	public void testDeleteFriends() {
		
		int exfriend_id = 5;
		int your_id = 19;
		
		// Delete the friend relation
		create.delete(USER_FRIENDS)
				.where(USER_FRIENDS.FRIENDEE_ID.equal(exfriend_id))
				.and(USER_FRIENDS.FRIENDER_ID.equal(your_id))
				.execute();
		
		create.delete(USER_FRIENDS)
				.where(USER_FRIENDS.FRIENDEE_ID.equal(your_id))
				.and(USER_FRIENDS.FRIENDER_ID.equal(exfriend_id))
		.		execute();
	}
	
	@Test
	public void testInsertFriendStash() {
		// Get User_ID from friender
		int friender_ID = 8;
		// Get User_ID from friendee
		int friendee_ID = 18;

		create.insertInto(FRIEND_REQUEST_STASH)
				.set(FRIEND_REQUEST_STASH.FRIENDER_ID, friender_ID)
				.set(FRIEND_REQUEST_STASH.FRIENDEE_ID, friendee_ID)
				.execute();

	}
	
	@Test
	public void testDeletetFriendStash() {
		
		int friend_id = 4;
		int your_id = 293;
		
		create.delete(FRIEND_REQUEST_STASH)
		.where(FRIEND_REQUEST_STASH.FRIENDER_ID.equal(your_id))
		.and(FRIEND_REQUEST_STASH.FRIENDEE_ID.equal(friend_id))
		.execute();
		
	}
	
	@Test
	public void testCalculateDistance() {
		DBManager dbm = new DBManager();
		
		 assertTrue(dbm.calculateDistance(47.266667, 11.383333, 47.273333, 11.241389) > 10 &&
	                dbm.calculateDistance(47.266667, 11.383333, 47.273333, 11.241389) < 12);
	}
	

}
