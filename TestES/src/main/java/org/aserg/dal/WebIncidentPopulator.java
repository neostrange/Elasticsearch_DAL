/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.Origin;
import org.aserg.model.WebIncident;
import org.aserg.model.WebRule;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Waseem
 *
 */
public class WebIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(WebIncidentPopulator.class);

	/**
	 * Function will populate Web Incidents objects
	 * 
	 * @return
	 */
	public List<WebIncident> populate() {

		log.info("Initiating WebIncident Population");
		List<WebIncident> webIncidentList = new ArrayList<WebIncident>();
		WebRule webRule = null;
		List<WebRule> webRuleList = null;
		WebIncident webIncident = null;
		String lastFetchTime = IOFileUtility.readProperty("webTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch web records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.WEB_INCIDENT_QUERY, SqlUtility.getWebConnection(),
				lastFetchTime);
		EnrichmentUtility.initLookupService();
		String prev = null;
		try {
			while (rs.next()) {

				// in case of new event
				if (!rs.getString("order_id").equals(prev)) {
					if (webIncident != null) {
						if (webRuleList != null) {
							webIncident.setRulesList(webRuleList);
						}
						webIncidentList.add(webIncident);
						log.debug("Added WebIncident to list, event_id [{}], rules triggered [{}] ",
								rs.getString("connection"), webRuleList.size());

					}
					Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
					org = org == null ? null : org;
					lastFetchTime = rs.getString("connection_datetime");
					webIncident = new WebIncident(lastFetchTime.replace(' ', 'T'), rs.getString("remote_host"),
							rs.getInt("a_server_port"), rs.getString("b_protocol"), rs.getString("client_ip"),
							rs.getInt("a_client_port"), "tcp", org, rs.getInt("f_content_length"),
							rs.getString("f_content_type"), rs.getString("b_method"), rs.getString("b_path_parameter"),
							rs.getString("b_referer"), null, rs.getInt("id_severity"), rs.getString("severity"),
							rs.getString("b_user_agent"));
					prev = rs.getString("order_id");
					// add webrule
					if (rs.getString("message_ruleMsg") != null) {
						webRuleList = new ArrayList<WebRule>();
						webRule = new WebRule("ruleCategory", rs.getString("message_ruleMsg"));

					}

				}
				// if event same as prev, just add webrule
				else {
					log.debug("WebIncident same as previous, event_id [{}] ", prev);
					webRule = new WebRule("ruleCategory", rs.getString("message_ruleMsg"));
					webRuleList.add(webRule);
				}

				// for last
				if (rs.isLast() && !webIncidentList.contains(webIncident)) {
					webIncidentList.add(webIncident);
					log.debug("Added WebIncident to list, event_id [{}], rules triggered [{}] ",
							rs.getString("connection"), webRuleList.size());
				}

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through web records ", e);
		}

		IOFileUtility.writeProperty("webTime", lastFetchTime, IOFileUtility.STATE_PATH);
		EnrichmentUtility.closeLookupService();
		SqlUtility.closeConnection(SqlUtility.getWebConnection());
		log.debug("Number of new web incidents [{}], since last fetched at [{}] ", webIncidentList.size(),
				lastFetchTime);
		log.info("WebIncident Population Successful");
		return webIncidentList;
	}

}
