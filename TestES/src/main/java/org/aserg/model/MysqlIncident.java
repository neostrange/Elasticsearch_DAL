package org.aserg.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class represents the {@code Incident} where the attacker exploited
 * exposed vulnerabilities of the MySQL service.
 */
public class MysqlIncident extends Incident {
	
	private static Logger log = LoggerFactory.getLogger(MysqlIncident.class);

	/**
	 * List of {@code MysqlCommand} which stores the list of MySQL commands
	 * carried out by the attacker in an incident and the corresponding
	 * operation number.
	 */
	private List<MysqlCommand> mysqlCommands;


	public MysqlIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, List<MysqlCommand> mysqlCommands) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		log.trace("Create new MysqlIncident");
		this.mysqlCommands = mysqlCommands;
	}

	public List<MysqlCommand> getMysqlCommands() {
		log.trace("Get mysqlCommands, returns [{}]", mysqlCommands);
		return mysqlCommands;
	}

	public void setMysqlCommands(List<MysqlCommand> mysqlCommands) {
		log.trace("Set mysqlCommands to [{}]", mysqlCommands);
		this.mysqlCommands = mysqlCommands;
	}

}