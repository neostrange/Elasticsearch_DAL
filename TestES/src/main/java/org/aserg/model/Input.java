package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton for representing the commands executed by attacker in
 * {@code SshIncident}.
 */
public class Input {

	private static Logger log = LoggerFactory.getLogger(Input.class);

	/**
	 * Command attempted by the attacker.
	 */
	private String command;
	/**
	 * Whether or not the command was executed successfully.
	 */
	private boolean success;
	/**
	 * The datetime when the command was executed.
	 */
	private String dateTime;

	public Input(String command, boolean success, String time) {
		log.trace("Create new Incident instance with command [{}], success [{}], time [{}] ", command, success,
				time);
		this.command = command;
		this.success = success;
		this.dateTime = time;
	}

	public String getCommand() {
		log.trace("Get command, returns [{}]", command);
		return command;
	}

	public void setCommand(String command) {
		log.trace("Set command to [{}]", command);
		this.command = command;
	}

	public boolean isSuccess() {
		log.trace("Input isSuccess, returns [{}]", success);
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