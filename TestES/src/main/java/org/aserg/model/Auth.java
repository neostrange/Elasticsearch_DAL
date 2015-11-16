package org.aserg.model;


/**
 * Singleton for representing an authentication attempt in {@code SshIncident}.
 */
public class Auth {

	/**
	 * Username used for authentication attempt.
	 */
	private String username;
	/**
	 * Password used for the authentication attempt.
	 */
	private String password;
	/**
	 * Whether or not the authentication attempt was successful.
	 */
	private boolean success;
	/**
	 * The datetime associated with the authentication attempt.
	 */
	private String dateTime;

	public Auth(String username, String password, boolean success, String time) {
		super();
		this.username = username;
		this.password = password;
		this.success = success;
		this.dateTime = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

}