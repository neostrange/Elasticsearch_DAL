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
	
	

	public MssqlIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, String mssql_clientname, String mssql_command_cmd,
			String mssql_command_status, String mssql_hostname) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.mssqlClientName = mssql_clientname;
		this.mssqlCommand = mssql_command_cmd;
		this.mssqlCommandStatus = mssql_command_status;
		this.mssqlHostName = mssql_hostname;
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