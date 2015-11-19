/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.util.List;

import org.aserg.model.Incident;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class SipIncidentPopulator implements IncidentPopulator {

	private SqlUtility dbUtility;
	private ResultSet resultSet;
	/**
	 * 
	 */
	public SipIncidentPopulator() {
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

	@Override
	public List<Incident> populate() {
		// TODO Auto-generated method stub
		return null;
	}

}
