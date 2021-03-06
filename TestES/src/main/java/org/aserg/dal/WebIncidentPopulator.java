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
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 
 * The data access class contains logic for fetching Web data from relational
 * DBs, normalizes and enriches the data, and creates {@link WebIncident}
 * objects that can be indexed into ElasticSearch
 *
 */
public class WebIncidentPopulator {

	/**
	 * The logger for the class
	 */
	private static Logger log = LoggerFactory.getLogger(WebIncidentPopulator.class);

	/**
	 * The function fetches Web data from the relational database and adds it to
	 * be indexed to BulkProcessor
	 * 
	 * @param index
	 *            the ElasticSearch index where the resultant document is to be
	 *            stored
	 * @param type
	 *            the ElasticSearch type where the resultant document is to be
	 *            stored
	 */
	public static void pushWebIncidents(String index, String type) {

		log.info("Initiating WebIncident Population");
		WebRule webRule = null;
		List<WebRule> webRuleList = null;
		WebIncident webIncident = null;
		String lastFetchTime = IOFileUtility.readProperty("webTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch web records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.WEB_INCIDENT_QUERY,
				SqlUtility.getMysqlConnection(IOFileUtility.readProperty("WEB_DB_NAME", IOFileUtility.ARCHIVAL_PATH),
						IOFileUtility.readProperty("WEB_PASSWORD", IOFileUtility.ARCHIVAL_PATH)),
				lastFetchTime);
		String prev = null;
		String ref = null;
		Origin org = null;
		int count = 0;
		try {
			while (rs.next()) {

				// in case of new event
				if (!rs.getString("order_id").equals(prev)) {
					if (webIncident != null) {
						if (webRuleList != null) {
							webIncident.setRulesList(webRuleList);
						}
						EsUtility.pushDocument(new Gson().toJson(webIncident), index, type);
						count++;
						log.debug("Added WebIncident to BulkProcessor, event_id [{}] ", rs.getString("order_id"));
						webRuleList = null;
					}

					org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
					org = org == null ? null : org;
					ref = rs.getString("b_referer");
					ref = ref.isEmpty() ? null : ref;
					lastFetchTime = rs.getString("connection_datetime");
					webIncident = new WebIncident(lastFetchTime.replace(' ', 'T'), rs.getString("remote_host"),
							rs.getInt("remote_port"), "web server", rs.getString("local_host"),
							rs.getInt("local_port"), rs.getString("b_protocol"), org, rs.getInt("f_content_length"),
							rs.getString("f_content_type"), rs.getString("b_method"), rs.getString("b_path_parameter"),
							ref, null, rs.getInt("id_severity"), rs.getString("severity"),
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

				// for the last record
				if (rs.isLast()) {
					EsUtility.pushDocument(new Gson().toJson(webIncident), index, type);
					count++;
					log.debug("Added WebIncident to list, event_id [{}], rules triggered [{}] ",
							rs.getString("order_id"));
				}

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through web records ", e);
		}
		SqlUtility.closeDbInstances(SqlUtility.mysqlConnection);
		// change time in state file only if there were any new incidents
		if (count > 0)
			IOFileUtility.writeProperty("webTime", lastFetchTime, IOFileUtility.STATE_PATH);
		else{
			log.debug("No new incidents added so time remains unchanged.");
		}
		log.info("Pushed [{}] new web incidents, since last fetched at [{}] ", count, lastFetchTime);
	}

	/**
	 * The function fetches Web data from the relational database and adds it to
	 * a List to be returned
	 * 
	 * @return list of {@link WebIncident}
	 */
	public List<WebIncident> populate() {

		log.info("Initiating WebIncident Population");
		List<WebIncident> webIncidentList = new ArrayList<WebIncident>();
		WebRule webRule = null;
		List<WebRule> webRuleList = null;
		WebIncident webIncident = null;
		String lastFetchTime = IOFileUtility.readProperty("webTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch web records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.WEB_INCIDENT_QUERY,
				SqlUtility.getMysqlConnection(IOFileUtility.readProperty("WEB_DB_NAME", IOFileUtility.ARCHIVAL_PATH),
						IOFileUtility.readProperty("WEB_PASSWORD", IOFileUtility.ARCHIVAL_PATH)),
				lastFetchTime);
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
						log.debug("Added WebIncident to list, event_id [{}] ", rs.getString("order_id"));

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
							rs.getString("order_id"));
				}

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through web records ", e);
		}

		IOFileUtility.writeProperty("webTime", lastFetchTime, IOFileUtility.STATE_PATH);
		SqlUtility.closeDbInstances(SqlUtility.mysqlConnection);
		log.debug("Number of new web incidents [{}], since last fetched at [{}] ", webIncidentList.size(),
				lastFetchTime);
		log.info("WebIncident Population Successful");
		return webIncidentList;
	}

}
