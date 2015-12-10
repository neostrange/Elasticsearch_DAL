package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.MssqlIncident;
import org.aserg.model.Origin;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * 
 * The data access class contains logic for fetching Mssql data from
 * relational DBs, normalizes and enriches the data, and creates
 * {@link MssqlIncident} objects that can be indexed into ElasticSearch
 *
 */
public class MssqlIncidentPopulator {

	/**
	 * The logger for this class
	 */
	private static Logger log = LoggerFactory.getLogger(MalwareIncidentPopulator.class);

	/**
	 * The function fetches Mssql data from the relational database and adds
	 * it to be indexed to BulkProcessor
	 * 
	 * @param index
	 *            the ElasticSearch index where the resultant document is to be
	 *            stored
	 * @param type
	 *            the ElasticSearch type where the resultant document is to be
	 *            stored
	 */
	public static void pushMssqlIncidents(String index, String type) {
		log.info("Initiating MssqlIncident Population");
		MssqlIncident mssqlIncident;
		String lastFetchTime = IOFileUtility.readProperty("mssqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mssql records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		Origin org = null;
		String remotehost = null;
		String localhost = null;
		int count = 0;
		try {
			while (rs.next()) {

				// Make sure dionaea doesn't tack on :ffff: before IP
				// addresses (Split at "f:" and get the latter part of the
				// address)
				remotehost = rs.getString("remote_host");
				remotehost = remotehost.contains(":") ? remotehost.split("f:")[1] : remotehost;
				localhost = rs.getString("local_host");
				localhost = localhost.contains(":") ? localhost.split("f:")[1] : localhost;

				org = EnrichmentUtility.getOrigin(remotehost);
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				mssqlIncident = new MssqlIncident(lastFetchTime.replace(' ', 'T'), remotehost, rs.getInt("remote_port"),
						rs.getString("connection_protocol"), localhost, rs.getInt("local_port"),
						rs.getString("connection_transport"), org, rs.getString("mssql_fingerprint_cltintname"),
						rs.getString("cmd"), rs.getString("status"), rs.getString("mssql_fingerprint_hostname"));
				EsUtility.pushDocument(new Gson().toJson(mssqlIncident), index, type);
				count++;
				log.debug("Added MssqlIncident to BulkProcessor, connection [{}]", rs.getString("order_id"));
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through mssql records", e);
		}
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		// change time in state file only if there were any new incidents
		if (count > 0)
			IOFileUtility.writeProperty("mssqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		else{
			log.info("No new incidents added so time remains unchanged.");
		}
		log.info("[{}] new mssql incidents, since last fetched at [{}] ", count, lastFetchTime);

	}

	/**
	 * The function fetches Mssql data from the relational database and adds
	 * it to a List to be returned
	 * 
	 * @return list of {@link MssqlIncident}
	 */
	public List<MssqlIncident> populate() {
		log.info("Initiating MssqlIncident Population");
		List<MssqlIncident> mssqlIncidentList = new ArrayList<MssqlIncident>();
		MssqlIncident mssqlIncident;
		String lastFetchTime = IOFileUtility.readProperty("mssqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mssql records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		Origin org = null;
		String remotehost = null;
		String localhost = null;
		try {
			while (rs.next()) {
				if (rs.getString("remote_host").contains(":"))
					remotehost = rs.getString("remote_host").split("f:")[1];
				else
					remotehost = rs.getString("remote_host");

				if (rs.getString("local_host").contains(":"))
					localhost = rs.getString("local_host").split("f:")[1];
				else
					localhost = rs.getString("local_host");

				org = EnrichmentUtility.getOrigin(remotehost);
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				mssqlIncident = new MssqlIncident(lastFetchTime.replace(' ', 'T'), remotehost, rs.getInt("remote_port"),
						rs.getString("connection_protocol"), localhost, rs.getInt("local_port"),
						rs.getString("connection_transport"), org, rs.getString("mssql_fingerprint_cltintname"),
						rs.getString("cmd"), rs.getString("status"), rs.getString("mssql_fingerprint_hostname"));
				mssqlIncidentList.add(mssqlIncident);
				log.debug("Added MssqlIncident to list, connection [{}]", rs.getString("order_id"));
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through mssql records", e);
		}
		log.debug("Number of new mssql incidents [{}], since last fetched at [{}] ", mssqlIncidentList.size(),
				lastFetchTime);
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		IOFileUtility.writeProperty("mssqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		log.info("MssqlIncident Population Successful");
		return mssqlIncidentList;
	}

}
