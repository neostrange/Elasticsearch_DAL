/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;

import org.aserg.model.Incident;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class NetworkLayerIncident implements IncidentPopulator {

	private SqlUtility dbUtility;
	private ResultSet resultSet;
	/**
	 * 
	 */
	public NetworkLayerIncident() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.aserg.dal.IncidentPopulator#populate(java.sql.ResultSet)
	 */
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

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

}