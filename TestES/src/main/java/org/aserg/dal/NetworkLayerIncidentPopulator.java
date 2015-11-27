/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.NetworkLayerIncident;
import org.aserg.model.Origin;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * @author Waseem
 *
 */
public class NetworkLayerIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(MalwareIncidentPopulator.class);

	public static void pushNetworkLayerIncidents(String index, String type) {
		log.info("Initiating NetworkLayerIncident Population");
		String lastFetchTime = IOFileUtility.readProperty("networkTime", IOFileUtility.STATE_PATH);
		NetworkLayerIncident networkLayerIncident = null;
		log.debug("Run query to fetch network records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.NETWORK_LAYER_INCIDENT_QUERY,
				SqlUtility.getMysqlConnection(
						IOFileUtility.readProperty("NETWORK_DB_NAME", IOFileUtility.ARCHIVAL_PATH),
						IOFileUtility.readProperty("NETWORK_PASSWORD", IOFileUtility.ARCHIVAL_PATH)),
				lastFetchTime);
		String icmp = null;
		String transport = null;
		String protocol = null;
		Origin org = null;
		int count = 0;
		try {
			while (rs.next()) {
				int localPort = rs.getInt("tcp_local_port") == 0 ? rs.getInt("udp_local_port")
						: rs.getInt("tcp_local_port");
				int remotePort = rs.getInt("tcp_remote_port") == 0 ? rs.getInt("udp_remote_port")
						: rs.getInt("tcp_remote_port");
						// transport = udp, if udp port is not null, OR tcp if
						// tcp port
						// is not null; else, it is null

				// in case of icmp
				if (rs.getString("icmp_type") != null) {
					icmp = rs.getString("icmp_type");
					transport = null;
					protocol = "icmp";

				} else {
					icmp = null;
					transport = rs.getInt("tcp_local_port") == 0 ? "udp" : "tcp";
					protocol = "protocol";

				}
				org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				networkLayerIncident = new NetworkLayerIncident(lastFetchTime.replace(' ', 'T'),
						rs.getString("remote_host"), remotePort, protocol, rs.getString("local_host"), localPort,
						transport, org, rs.getInt("order_id"), rs.getInt("sid"), rs.getString("sig_name"),
						rs.getString("sig_class_name"), icmp);
				EsUtility.pushDocument(new Gson().toJson(networkLayerIncident), index, type);
				count++;
				log.debug("Added NetworkLayerIncident to list , cid [{}], sid [{}]", rs.getString("order_id"),
						rs.getString("sid"));

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through network ResultSet", e);
		}

		SqlUtility.closeDbInstances(SqlUtility.mysqlConnection);
		IOFileUtility.writeProperty("networkTime", lastFetchTime, IOFileUtility.STATE_PATH);
		log.debug("Number of new network incidents [{}], since last fetched at [{}] ", count, lastFetchTime);
		log.info("NetworkLayerIncident Population Successful");
	}

	public List<NetworkLayerIncident> populate() {
		log.info("Initiating NetworkLayerIncident Population");
		List<NetworkLayerIncident> networkLayerIncidentList = new ArrayList<NetworkLayerIncident>();
		String lastFetchTime = IOFileUtility.readProperty("networkTime", IOFileUtility.STATE_PATH);
		NetworkLayerIncident networkLayerIncident = null;
		log.debug("Run query to fetch network records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.NETWORK_LAYER_INCIDENT_QUERY,
				SqlUtility.getMysqlConnection(
						IOFileUtility.readProperty("NETWORK_DB_NAME", IOFileUtility.ARCHIVAL_PATH),
						IOFileUtility.readProperty("NETWORK_PASSWORD", IOFileUtility.ARCHIVAL_PATH)),
				lastFetchTime);
		String type = null;
		String transport = null;
		String protocol = null;
		Origin org = null;
		try {
			while (rs.next()) {
				int localPort = rs.getInt("tcp_local_port") == 0 ? rs.getInt("udp_local_port")
						: rs.getInt("tcp_local_port");
				int remotePort = rs.getInt("tcp_remote_port") == 0 ? rs.getInt("udp_remote_port")
						: rs.getInt("tcp_remote_port");
						// transport = udp, if udp port is not null, OR tcp if
						// tcp port
						// is not null; else, it is null

				// in case of icmp
				if (rs.getString("icmp_type") != null) {
					type = rs.getString("icmp_type");
					transport = null;
					protocol = "icmp";

				} else {
					type = null;
					transport = rs.getInt("tcp_local_port") == 0 ? "udp" : "tcp";
					protocol = "protocol";

				}
				org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null ? null : org;
				lastFetchTime = rs.getString("connection_datetime");
				networkLayerIncident = new NetworkLayerIncident(lastFetchTime.replace(' ', 'T'),
						rs.getString("remote_host"), remotePort, protocol, rs.getString("local_host"), localPort,
						transport, org, rs.getInt("order_id"), rs.getInt("sid"), rs.getString("sig_name"),
						rs.getString("sig_class_name"), type);
				networkLayerIncidentList.add(networkLayerIncident);
				log.debug("Added NetworkLayerIncident to list , cid [{}], sid [{}]", rs.getString("order_id"),
						rs.getString("sid"));

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through network ResultSet", e);
		}

		SqlUtility.closeDbInstances(SqlUtility.mysqlConnection);
		IOFileUtility.writeProperty("networkTime", lastFetchTime, IOFileUtility.STATE_PATH);
		log.debug("Number of new network incidents [{}], since last fetched at [{}] ", networkLayerIncidentList.size(),
				lastFetchTime);
		log.info("NetworkLayerIncident Population Successful");
		return networkLayerIncidentList;
	}

}
