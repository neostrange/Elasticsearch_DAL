package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;

public interface IncidentPopulator {
	
	public Incident populate(ResultSet resultSet);
	

}
