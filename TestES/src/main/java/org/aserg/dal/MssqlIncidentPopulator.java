package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;
import org.aserg.utility.SqlUtility;

public class MssqlIncidentPopulator implements IncidentPopulator {
	
	private SqlUtility dbUtility;
	private ResultSet resultSet;

	public MssqlIncidentPopulator() {
			// TODO Auto-generated constructor stub
	}

	public Incident populate(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public SqlUtility getDbUtility() {
		return dbUtility;
	}

	public void setDbUtility(SqlUtility dbUtility) {
		this.dbUtility = dbUtility;
	}

}
