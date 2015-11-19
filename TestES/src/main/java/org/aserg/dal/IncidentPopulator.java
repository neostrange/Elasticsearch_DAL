package org.aserg.dal;

import java.sql.ResultSet;
import java.util.List;

import org.aserg.model.Incident;

public interface IncidentPopulator {
	
	public List<Incident> populate();
	

}
