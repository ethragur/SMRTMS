/**
 * This class is generated by jOOQ
 */
package jooqdb.tables.records;


import javax.annotation.Generated;

import jooqdb.tables.User;

import org.jooq.Field;
import org.jooq.Record8;
import org.jooq.Row;
import org.jooq.Row8;
import org.jooq.impl.TableRecordImpl;


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
public class UserRecord extends TableRecordImpl<UserRecord> implements Record8<Integer, String, String, String, Double, Double, String, Byte> {

	private static final long serialVersionUID = 1091592981;

	/**
	 * Setter for <code>SMRTMS.User.ID</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.ID</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>SMRTMS.User.Username</code>.
	 */
	public void setUsername(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Username</code>.
	 */
	public String getUsername() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>SMRTMS.User.Email</code>.
	 */
	public void setEmail(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Email</code>.
	 */
	public String getEmail() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>SMRTMS.User.Password</code>.
	 */
	public void setPassword(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Password</code>.
	 */
	public String getPassword() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>SMRTMS.User.Longitude</code>.
	 */
	public void setLongitude(Double value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Longitude</code>.
	 */
	public Double getLongitude() {
		return (Double) getValue(4);
	}

	/**
	 * Setter for <code>SMRTMS.User.Latitude</code>.
	 */
	public void setLatitude(Double value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Latitude</code>.
	 */
	public Double getLatitude() {
		return (Double) getValue(5);
	}

	/**
	 * Setter for <code>SMRTMS.User.Avatar</code>.
	 */
	public void setAvatar(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.Avatar</code>.
	 */
	public String getAvatar() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>SMRTMS.User.IsOnline</code>.
	 */
	public void setIsonline(Byte value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>SMRTMS.User.IsOnline</code>.
	 */
	public Byte getIsonline() {
		return (Byte) getValue(7);
	}

	// -------------------------------------------------------------------------
	// Record8 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, String, String, String, Double, Double, String, Byte> fieldsRow() {
		return (Row8) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row8<Integer, String, String, String, Double, Double, String, Byte> valuesRow() {
		return (Row8) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return User.USER.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return User.USER.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return User.USER.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return User.USER.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field5() {
		return User.USER.LONGITUDE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Double> field6() {
		return User.USER.LATITUDE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return User.USER.AVATAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Byte> field8() {
		return User.USER.ISONLINE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value5() {
		return getLongitude();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double value6() {
		return getLatitude();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getAvatar();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Byte value8() {
		return getIsonline();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value2(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value3(String value) {
		setEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value4(String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value5(Double value) {
		setLongitude(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value6(Double value) {
		setLatitude(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value7(String value) {
		setAvatar(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord value8(Byte value) {
		setIsonline(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRecord values(Integer value1, String value2, String value3, String value4, Double value5, Double value6, String value7, Byte value8) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserRecord
	 */
	public UserRecord() {
		super(User.USER);
	}

	/**
	 * Create a detached, initialised UserRecord
	 */
	public UserRecord(Integer id, String username, String email, String password, Double longitude, Double latitude, String avatar, Byte isonline) {
		super(User.USER);

		setValue(0, id);
		setValue(1, username);
		setValue(2, email);
		setValue(3, password);
		setValue(4, longitude);
		setValue(5, latitude);
		setValue(6, avatar);
		setValue(7, isonline);
	}
}
