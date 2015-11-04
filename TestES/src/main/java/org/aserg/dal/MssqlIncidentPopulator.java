package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;

public class MssqlIncidentPopulator implements IncidentPopulator {
	
	private DbUtility dbUtility;
	private ResultSet resultSet;

	public MssqlIncidentPopulator() {
			// TODO Auto-generated constructor stub
	}

	public Incident populate(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public DbUtility getDbUtility() {
		return dbUtility;
	}

	public void setDbUtility(DbUtility dbUtility) {
		this.dbUtility = dbUtility;
	}

}
