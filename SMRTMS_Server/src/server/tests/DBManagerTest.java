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
	        
	        // Exceptions are propagated through the JDBC and jOOQ APIs
	        if (sql.toUpperCase().startsWith("DROP")) {
	            throw new SQLException("Statement not supported: " + sql);
	        }
	        
	        // You decide, whether any given statement returns results, and how many
	        else if (sql.toUpperCase().startsWith("SELECT")) {
	            
	           /* // Always return one author record
	            Result<AuthorRecord> result = create.newResult(AUTHOR);
	            result.add(create.newRecord(AUTHOR));
	            result.get(0).setValue(AUTHOR.ID, 1);
	            result.get(0).setValue(AUTHOR.LAST_NAME, "Orwell");
	            mock[0] = new MockResult(1, result);*/
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
	public void blubb() {
		// Initialise your data provider (implementation further down):
		provider = new MyProvider();
		connection = new MockConnection(provider);

		// Pass the mock connection to a jOOQ DSLContext:
		create = DSL.using(connection, SQLDialect.MYSQL);
	}

	@Test
	public void test() {
		
		// Execute queries transparently, with the above DSLContext:
		Result<Record> result = create.select().from(USER).fetch();
		
		fail("Not yet implemented");
	}

}
