package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.MssqlIncident;
import org.aserg.model.Origin;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MssqlIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(MalwareIncidentPopulator.class);

	public List<MssqlIncident> populate() {
		log.info("Initiating MssqlIncident Population");
		List<MssqlIncident> mssqlIncidentList = new ArrayList<MssqlIncident>();
		MssqlIncident mssqlIncident;
		String lastFetchTime = IOFileUtility.readProperty("mssqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mssql records");
		EnrichmentUtility.initLookupService();
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.getDionaeaConnection(),
				lastFetchTime);
		IOFileUtility.writeProperty("mssqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		Origin org = null;
		try {
			while (rs.next()) {
				org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				mssqlIncident = new MssqlIncident(lastFetchTime.replace(' ', 'T'), rs.getString("remote_host"),
						rs.getInt("remote_port"), rs.getString("connection_protocol"), rs.getString("local_host"),
						rs.getInt("local_port"), rs.getString("connection_transport"), org,
						rs.getString("mssql_fingerprint_cltintname"), rs.getString("cmd"), rs.getString("status"),
						rs.getString("mssql_fingerprint_hostname"));
				mssqlIncidentList.add(mssqlIncident);
				log.debug("Added MssqlIncident to list, connection [{}]", rs.getString("connection"));
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through mssql records", e);
		}
		EnrichmentUtility.closeLookupService();
		log.debug("Number of new mssql incidents [{}], since last fetched at [{}] ", mssqlIncidentList.size(),
				lastFetchTime);
		SqlUtility.closeConnection(SqlUtility.getDionaeaConnection());
		log.info("MssqlIncident Population Successful");
		return mssqlIncidentList;
	}

}
