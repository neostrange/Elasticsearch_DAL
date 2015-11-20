package org.aserg.model;

/**
 * This class represents the {@code Incident} that where the attacker attempts
 * to exploit the vulnerabilities emulated in the MSSQL service.
 */
public class MssqlIncident extends Incident {

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
		this.mssqlClientName = mssqlClientName;
		this.mssqlCommand = mssqlCommand;
		this.mssqlCommandStatus = mssqlCommandStatus;
		this.mssqlHostName = mssqlHostName;
	}

	public String getMssqlClientName() {
		return mssqlClientName;
	}

	public void setMssqlClientName(String mssql_clientname) {
		this.mssqlClientName = mssql_clientname;
	}

	public String getMssqlCommand() {
		return mssqlCommand;
	}

	public void setMssqlCommand(String mssql_command_cmd) {
		this.mssqlCommand = mssql_command_cmd;
	}

	public String getMssqlCommandStatus() {
		return mssqlCommandStatus;
	}

	public void setMssqlCommandStatus(String mssql_command_status) {
		this.mssqlCommandStatus = mssql_command_status;
	}

	public String getMssqlHostName() {
		return mssqlHostName;
	}

	public void setMssqlHostName(String mssql_hostname) {
		this.mssqlHostName = mssql_hostname;
	}

}