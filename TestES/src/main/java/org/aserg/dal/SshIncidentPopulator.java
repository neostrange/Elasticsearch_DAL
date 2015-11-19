package org.aserg.dal;

import java.sql.ResultSet;
import java.util.List;

import org.aserg.model.Incident;
import org.aserg.utility.SqlUtility;

public class SshIncidentPopulator implements IncidentPopulator {
	
	private SqlUtility dbUtility;
	private ResultSet resultSet;
	
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
	
	// domain objects to be initialized here
	
	
	public void SshIncidentPopulate(){
		
	}

	public Incident populate(ResultSet resultSet){
		return null;
		
		// logic to populate SshIncident domain objects
		
	}

	@Override
	public List<Incident> populate() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
