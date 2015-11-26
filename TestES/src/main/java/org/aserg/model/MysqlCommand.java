package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton for storing MySQL commands for {@code MysqlIncident}.
 */
public class MysqlCommand {
	
	private static Logger log = LoggerFactory.getLogger(MysqlCommand.class);

	/**
	 * The MySQL query executed by the attacker.
	 */
	private String mysqlQuery;
	/**
	 * The op number associated with MySQL query.
	 */
	private String mysqlOp;
	
	
	public MysqlCommand(String mysqlQuery, String mysqlOp) {
		log.trace("Create new MysqlCommand instance");
		this.mysqlQuery = mysqlQuery;
		this.mysqlOp = mysqlOp;
	}
	
	public String getMysqlQuery() {
		log.trace("Get mysqlQuery, returns [{}]", mysqlQuery);
		return mysqlQuery;
	}
	public void setMysqlQuery(String mysqlQuery) {
		log.trace("Set mysqlQuery to [{}]", mysqlQuery);
		this.mysqlQuery = mysqlQuery;
	}
	public String getMysqlOp() {
		log.trace("Get mysqlOp, returns [{}]", mysqlOp);
		return mysqlOp;
	}
	public void setMysqlOp(String mysqlOp) {
		log.trace("Set mysqlOp to [{}]", mysqlOp);
		this.mysqlOp = mysqlOp;
	}


}