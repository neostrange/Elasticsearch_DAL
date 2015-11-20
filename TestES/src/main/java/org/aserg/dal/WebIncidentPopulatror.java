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

/**
 * @author Waseem
 *
 */
public class WebIncidentPopulatror {

	List<WebIncident> webIncidentList = new ArrayList<WebIncident>();
	WebRule webRule = null;
	List<WebRule> webRuleList = null;
	WebIncident webIncident = null;
/**
 * Function will populate Web Incidents objects
 * @return
 */
	public List<WebIncident> populate() {
		
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.WEB_INCIDENT_QUERY, SqlUtility.webConnection,
				IOFileUtility.readTime("webTime"));
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
					}
					// TODO enrichmentutil geolocation and populate origin
					Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
					org = org == null? null: org;
					webIncident = new WebIncident(rs.getString("connection_datetime").replace(' ', 'T'), rs.getString("remote_host"),
							rs.getInt("a_server_port"), rs.getString("b_protocol"), rs.getString("client_ip"),
							rs.getInt("a_client_port"), "tcp", org, rs.getInt("f_content_length"),
							rs.getString("f_content_type"), rs.getString("b_method"), rs.getString("b_path_parameter"),
							rs.getString("b_referer"), null, rs.getInt("id_severity"), rs.getString("severity"),
							rs.getString("b_user_agent"));
					IOFileUtility.writeTime("webTime", rs.getString("connection_datetime"));
					prev = rs.getString("order_id");
					// add webrule
					if (rs.getString("message_ruleMsg") != null) {
						webRuleList = new ArrayList<WebRule>();
						webRule = new WebRule("ruleCategory", rs.getString("message_ruleMsg"));
					}

				}
				// if event same as prev, just add webrule
				else {
					webRule = new WebRule("ruleCategory", rs.getString("message_ruleMsg"));
					webRuleList.add(webRule);
				}

				// for last
				if (rs.isLast() && !webIncidentList.contains(webIncident)) {
					webIncidentList.add(webIncident);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return webIncidentList;
	}
	

}
