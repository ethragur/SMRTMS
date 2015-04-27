/**
 * This class is generated by jOOQ
 */
package jooqdb.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooqdb.Keys;
import jooqdb.Smrtms;
import jooqdb.tables.records.UserRecord;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.0"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class User extends TableImpl<UserRecord> {

	private static final long serialVersionUID = -1896175390;

	/**
	 * The reference instance of <code>SMRTMS.User</code>
	 */
	public static final User USER = new User();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserRecord> getRecordType() {
		return UserRecord.class;
	}

	/**
	 * The column <code>SMRTMS.User.ID</code>.
	 */
	public final TableField<UserRecord, Integer> ID = createField("ID", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>SMRTMS.User.First_Name</code>.
	 */
	public final TableField<UserRecord, String> FIRST_NAME = createField("First_Name", org.jooq.impl.SQLDataType.CHAR.length(1), this, "");

	/**
	 * The column <code>SMRTMS.User.Last_Name</code>.
	 */
	public final TableField<UserRecord, String> LAST_NAME = createField("Last_Name", org.jooq.impl.SQLDataType.CHAR.length(1), this, "");

	/**
	 * The column <code>SMRTMS.User.Password</code>.
	 */
	public final TableField<UserRecord, String> PASSWORD = createField("Password", org.jooq.impl.SQLDataType.CHAR.length(1), this, "");

	/**
	 * The column <code>SMRTMS.User.Position</code>.
	 */
	public final TableField<UserRecord, Integer> POSITION = createField("Position", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>SMRTMS.User.Avatar</code>.
	 */
	public final TableField<UserRecord, String> AVATAR = createField("Avatar", org.jooq.impl.SQLDataType.CHAR.length(1), this, "");

	/**
	 * Create a <code>SMRTMS.User</code> table reference
	 */
	public User() {
		this("User", null);
	}

	/**
	 * Create an aliased <code>SMRTMS.User</code> table reference
	 */
	public User(String alias) {
		this(alias, USER);
	}

	private User(String alias, Table<UserRecord> aliased) {
		this(alias, aliased, null);
	}

	private User(String alias, Table<UserRecord> aliased, Field<?>[] parameters) {
		super(alias, Smrtms.SMRTMS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UserRecord>> getKeys() {
		return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User as(String alias) {
		return new User(alias, this);
	}

	/**
	 * Rename this table
	 */
	public User rename(String name) {
		return new User(name, null);
	}
}
