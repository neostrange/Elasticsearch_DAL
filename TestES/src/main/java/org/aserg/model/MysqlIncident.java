package org.aserg.model;

import java.util.Date;
import java.util.List;

import org.elasticsearch.common.joda.time.DateTime;

/**
 * This class represents the {@code Incident} where the attacker exploited
 * exposed vulnerabilities of the MySQL service.
 */
public class MysqlIncident extends Incident {

	/**
	 * List of {@code MysqlCommand} which stores the list of MySQL commands
	 * carried out by the attacker in an incident and the corresponding
	 * operation number.
	 */
	private List<MysqlCommand> mysqlCommands;

	public MysqlIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, List<MysqlCommand> mysqlCommands) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.mysqlCommands = mysqlCommands;
	}

	public List<MysqlCommand> getMysqlCommands() {
		return mysqlCommands;
	}

	public void setMysqlCommands(List<MysqlCommand> mysqlCommands) {
		this.mysqlCommands = mysqlCommands;
	}

}