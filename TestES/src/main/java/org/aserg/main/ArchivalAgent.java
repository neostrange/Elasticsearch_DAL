/**
 * 
 */
package org.aserg.main;

import java.sql.ResultSet;

import org.aserg.dal.MalwareIncidentPopulator;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class ArchivalAgent {

	/**
	 * 
	 */
	public ArchivalAgent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MALWARE_INCIDENT_QUERY,SqlUtility.dionaeaConnection);
		new MalwareIncidentPopulator().populate(rs);
		

	}

}
