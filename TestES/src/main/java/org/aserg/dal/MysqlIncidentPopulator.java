package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;

public class MysqlIncidentPopulator implements IncidentPopulator {

	private DbUtility dbUtility;
	private ResultSet resultSet;
	
	public MysqlIncidentPopulator() {
		// TODO Auto-generated constructor stub
	}
	public DbUtility getDbUtility() {
		return dbUtility;
	}

	public void setDbUtility(DbUtility dbUtility) {
		this.dbUtility = dbUtility;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public Incident populate(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

}
