/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.Origin;
import org.aserg.model.SipIncident;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class SipIncidentPopulator{
	

	public List<SipIncident> populate() {
		
		List<SipIncident> sipIncidentList = new ArrayList<SipIncident>();
		SipIncident sipIncident;
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.SIP_INCIDENT_QUERY, SqlUtility.getDionaeaConnection(),
				IOFileUtility.readTime("sipTime"));
		try {
			while (rs.next()) {
				// TODO enrichmentutil geolocation and populate origin
				Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null? null: org;
				String datetime = rs.getString("connection_datetime").replace(' ', 'T');
				sipIncident = new SipIncident(datetime, rs.getString("remote_host"), rs.getInt("remote_port"),
						rs.getString("connection_protocol"), rs.getString("local_host"), rs.getInt("local_port"),
						rs.getString("connection_transport"), org, rs.getString("sip_command_call_id"),
						rs.getString("sip_command_method"), rs.getString("sip_command_user_agent"));
				
				IOFileUtility.writeTime("sipTime", rs.getString("connection_datetime"));
				sipIncidentList.add(sipIncident);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlUtility.closeConnection(SqlUtility.getDionaeaConnection());
		return sipIncidentList;
	}

}
