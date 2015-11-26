package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the {@code Incident} that where the attacker attempts
 * to exploit the vulnerabilities emulated in the MSSQL service.
 */
public class MssqlIncident extends Incident {
	
	private static Logger log = LoggerFactory.getLogger(MssqlIncident.class);

	/**
	 * MSSQL client name.
	 */
	private String mssqlClientName;
	/**
	 * MSSQL command executed by the attacker.
	 */
	private String mssqlCommand;
	/**
	 * MSSQL command status.
	 */
	private String mssqlCommandStatus;
	/**
	 * MSSQL hostname used by the attacker.
	 */
	private String mssqlHostName;

	public MssqlIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin origin, String mssqlClientName, String mssqlCommand, String mssqlCommandStatus,
			String mssqlHostName) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, origin);
		log.trace("Create new MssqlIncident instance with mssqlClientName [{}], mssqlCommandStatus [{}]", mssqlClientName, mssqlCommandStatus);
		this.mssqlClientName = mssqlClientName;
		this.mssqlCommand = mssqlCommand;
		this.mssqlCommandStatus = mssqlCommandStatus;
		this.mssqlHostName = mssqlHostName;
	}

	public String getMssqlClientName() {
		log.trace("Get mssqlClientName, returns [{}]", mssqlClientName);
		return mssqlClientName;
	}

	public void setMssqlClientName(String mssql_clientname) {
		log.trace("Set mssqlClientName to [{}]", mssql_clientname);
		this.mssqlClientName = mssql_clientname;
	}

	public String getMssqlCommand() {
		log.trace("Get mssqlCommand, returns [{}]", mssqlCommand);
		return mssqlCommand;
	}

	public void setMssqlCommand(String mssql_command_cmd) {
		log.trace("Set mssqlCommand to [{}]", mssql_command_cmd);
		this.mssqlCommand = mssql_command_cmd;
	}

	public String getMssqlCommandStatus() {
		log.trace("Get mssqlCommandStatus, returns [{}]", mssqlCommandStatus);
		return mssqlCommandStatus;
	}

	public void setMssqlCommandStatus(String mssql_command_status) {
		log.trace("Set mssqlCommandStatus to [{}]", mssql_command_status);
		this.mssqlCommandStatus = mssql_command_status;
	}

	public String getMssqlHostName() {
		log.trace("Get mssqlHostName, returns [{}]", mssqlHostName);
		return mssqlHostName;
	}

	public void setMssqlHostName(String mssql_hostname) {
		log.trace("Set mssqlHostName to [{}]", mssql_hostname);
		this.mssqlHostName = mssql_hostname;
	}

}