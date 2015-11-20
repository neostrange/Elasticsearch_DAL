/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.SipIncident;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class SipIncidentPopulator{
	
	List<SipIncident> sipIncidentList = new ArrayList<SipIncident>();
	SipIncident sipIncident;

	public List<SipIncident> populate() {
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.SIP_INCIDENT_QUERY, SqlUtility.dionaeaConnection,
				IOFileUtility.readTime("sipTime"));
		try {
			while (rs.next()) {

				String datetime = rs.getString("connection_datetime").replace(' ', 'T');
				String remotecountry = EnrichmentUtility.getCountry(rs.getString("remote_host"));
				SipIncident sipIncident = new SipIncident(datetime, rs.getString("local_host"), rs.getInt("local_port"),
						rs.getString("connection_protocol"), rs.getString("remote_host"), rs.getInt("remote_port"),
						rs.getString("connection_transport"), remotecountry, rs.getString("sip_command_call_id"),
						rs.getString("sip_command_method"), rs.getString("sip_command_user_agent"));
				
				IOFileUtility.writeTime("sipTime", rs.getString("connection_datetime"));
				sipIncidentList.add(sipIncident);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sipIncidentList;
	}

}
