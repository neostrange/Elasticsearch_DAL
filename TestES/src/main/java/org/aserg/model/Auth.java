package org.aserg.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton for representing an authentication attempt in {@code SshIncident}.
 */
public class Auth {

	private static Logger log = LoggerFactory.getLogger(Auth.class);

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
		log.trace("Create new Auth instance with username [{}], password [{}], success [{}], dateTime [{}]", username,
				password, success, time);
		this.username = username;
		this.password = password;
		this.success = success;
		this.dateTime = time;
	}

	public String getUsername() {
		log.trace("Get username, returns [{}]", username);
		return username;
	}

	public void setUsername(String username) {
		log.trace("Set username to [{}]", username);
		this.username = username;
	}

	public String getPassword() {
		log.trace("Get password, returns [{}]", password);
		return password;
	}

	public void setPassword(String password) {
		log.trace("Set password to [{}]", password);
		this.password = password;
	}

	public boolean isSuccess() {
		log.trace("Auth isSuccess, returns [{}]", success);
		return success;
	}

	public void setSuccess(boolean success) {
		log.trace("Set success to [{}]", success);
		this.success = success;
	}

	public String getDateTime() {
		log.trace("Get dateTime, returns [{}]", dateTime);
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		log.trace("Set dateTime to [{}]", dateTime);
		this.dateTime = dateTime;
	}

}