package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;
import org.aserg.utility.SqlUtility;

public class MysqlIncidentPopulator implements IncidentPopulator {

	private SqlUtility dbUtility;
	private ResultSet resultSet;
	
	public MysqlIncidentPopulator() {
		// TODO Auto-generated constructor stub
	}
	public SqlUtility getDbUtility() {
		return dbUtility;
	}

	public void setDbUtility(SqlUtility dbUtility) {
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
