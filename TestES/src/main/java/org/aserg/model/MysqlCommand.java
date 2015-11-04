package org.aserg.model;


/**
 * Singleton for storing MySQL commands for {@code MysqlIncident}.
 */
public class MysqlCommand {

	/**
	 * The MySQL query executed by the attacker.
	 */
	private String mysqlQuery;
	/**
	 * The op number associated with MySQL query.
	 */
	private String mysqlOp;
	
	
	public MysqlCommand(String mysqlQuery, String mysqlOp) {
		this.mysqlQuery = mysqlQuery;
		this.mysqlOp = mysqlOp;
	}
	
	public String getMysqlQuery() {
		return mysqlQuery;
	}
	public void setMysqlQuery(String mysqlQuery) {
		this.mysqlQuery = mysqlQuery;
	}
	public String getMysqlOp() {
		return mysqlOp;
	}
	public void setMysqlOp(String mysqlOp) {
		this.mysqlOp = mysqlOp;
	}


}