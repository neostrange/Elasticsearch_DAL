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

public class MssqlIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(MalwareIncidentPopulator.class);

	public static void pushMssqlIncidents(String index, String type) {
		log.info("Initiating MssqlIncident Population");
		MssqlIncident mssqlIncident;
		String lastFetchTime = IOFileUtility.readProperty("mssqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mssql records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		Origin org = null;
		String remotehost = null;
		int count = 0;
		try {
			while (rs.next()) {
				if(rs.getString("remote_host").contains(":"))
					remotehost= rs.getString("remote_host").split("f:")[1];
				else
					remotehost= rs.getString("remote_host");
				org = EnrichmentUtility.getOrigin(remotehost);
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				mssqlIncident = new MssqlIncident(lastFetchTime.replace(' ', 'T'), remotehost,
						rs.getInt("remote_port"), rs.getString("connection_protocol"), rs.getString("local_host"),
						rs.getInt("local_port"), rs.getString("connection_transport"), org,
						rs.getString("mssql_fingerprint_cltintname"), rs.getString("cmd"), rs.getString("status"),
						rs.getString("mssql_fingerprint_hostname"));
				EsUtility.pushDocument(new Gson().toJson(mssqlIncident), index, type);
				count++;
				log.debug("Added MssqlIncident to list, connection [{}]", rs.getString("order_id"));
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through mssql records", e);
		}
		log.debug("Number of new mssql incidents [{}], since last fetched at [{}] ", count,
				lastFetchTime);
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		IOFileUtility.writeProperty("mssqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		log.info("MssqlIncident Population Successful");
	}
	
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
		try {
			while (rs.next()) {
				if(rs.getString("remote_host").contains(":"))
					remotehost= rs.getString("remote_host").split("f:")[1];
				else
					remotehost= rs.getString("remote_host");
				org = EnrichmentUtility.getOrigin(remotehost);
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				mssqlIncident = new MssqlIncident(lastFetchTime.replace(' ', 'T'), remotehost,
						rs.getInt("remote_port"), rs.getString("connection_protocol"), rs.getString("local_host"),
						rs.getInt("local_port"), rs.getString("connection_transport"), org,
						rs.getString("mssql_fingerprint_cltintname"), rs.getString("cmd"), rs.getString("status"),
						rs.getString("mssql_fingerprint_hostname"));
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
