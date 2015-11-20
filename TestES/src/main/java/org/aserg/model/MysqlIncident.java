package org.aserg.model;

import java.util.List;


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


	public MysqlIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, List<MysqlCommand> mysqlCommands) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		this.mysqlCommands = mysqlCommands;
	}

	public List<MysqlCommand> getMysqlCommands() {
		return mysqlCommands;
	}

	public void setMysqlCommands(List<MysqlCommand> mysqlCommands) {
		this.mysqlCommands = mysqlCommands;
	}

}