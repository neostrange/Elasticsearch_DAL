package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;

public class SshIncidentPopulator implements IncidentPopulator {
	
	private DbUtility dbUtility;
	private ResultSet resultSet;
	
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
	
	// domain objects to be initialized here
	
	
	public void SshIncidentPopulate(){
		
	}

	public Incident populate(ResultSet resultSet){
		return null;
		
		// logic to populate SshIncident domain objects
		
	}
	

}
