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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Waseem
 *
 */
public class SipIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(SipIncidentPopulator.class);

	public List<SipIncident> populate() {
		log.info("Initiating SipIncident Population");
		List<SipIncident> sipIncidentList = new ArrayList<SipIncident>();
		String lastFetchTime = IOFileUtility.readProperty("sipTime", IOFileUtility.STATE_PATH);
		SipIncident sipIncident;
		String datetime = null;
		log.debug("Run query to fetch sip records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.SIP_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		Origin org = null;
		String remotehost = null;
		try {
			while (rs.next()) {
				
				remotehost= rs.getString("cmc.remote_host").split(":f")[1];
				org = EnrichmentUtility.getOrigin(remotehost);
				org = org == null ? null : org;
				datetime = rs.getString("connection_datetime");
				sipIncident = new SipIncident(datetime.replace(' ', 'T'), remotehost, rs.getInt("remote_port"),
						rs.getString("connection_protocol"), rs.getString("local_host"), rs.getInt("local_port"),
						rs.getString("connection_transport"), org, rs.getString("sip_command_call_id"),
						rs.getString("sip_command_method"), rs.getString("sip_command_user_agent"));

				sipIncidentList.add(sipIncident);
				log.debug("Added SipIncident to list, connection [{}]", rs.getString("order_id"));
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through sip records ", e);
		}
		log.debug("Number of new sip incidents [{}], since last fetched at [{}] ", sipIncidentList.size(),
				lastFetchTime);
		IOFileUtility.writeProperty("sipTime", datetime, IOFileUtility.STATE_PATH);
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		log.info("SipIncident Population Successful");
		return sipIncidentList;
	}

}
