/**
 * 
 */
package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.NetworkLayerIncident;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;

/**
 * @author Waseem
 *
 */
public class NetworkLayerIncidentPopulator{

	List<NetworkLayerIncident> networkLayerIncidentList = new ArrayList<NetworkLayerIncident>();
	NetworkLayerIncident networkLayerIncident = null;

	public List<NetworkLayerIncident> populate() {
		
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.NETWORK_LAYER_INCIDENT_QUERY, SqlUtility.netConnection,
				IOFileUtility.readTime("networkTime"));
		
		String type = null;
		String transport = null;
		String protocol = null;
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
				networkLayerIncident = new NetworkLayerIncident(rs.getString("connection_datetime").replace(' ', 'T'), rs.getString("local_host"),
						localPort, protocol, EnrichmentUtility.getCountry(rs.getString("remote_host")), remotePort, transport,
						EnrichmentUtility.getCountry(rs.getString("remote_host")), rs.getInt("cid"), rs.getInt("sid"),
						rs.getString("sig_name"), rs.getString("sig_class_name"), type);

				IOFileUtility.writeTime("networkTime", rs.getString("connection_datetime"));
				networkLayerIncidentList.add(networkLayerIncident);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return networkLayerIncidentList;
	}

}
