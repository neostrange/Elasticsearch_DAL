package org.aserg.model;

/**
 * Singleton for representing the commands executed by attacker in
 * {@code SshIncident}.
 */
public class Input {

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
		super();
		this.command = command;
		this.success = success;
		this.dateTime = time;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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