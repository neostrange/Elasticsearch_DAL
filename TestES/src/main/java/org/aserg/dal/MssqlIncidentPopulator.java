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

public class MssqlIncidentPopulator {

	

	public MssqlIncidentPopulator() {
		// TODO Auto-generated constructor stub
	}

	public List<MssqlIncident> populate() {

		List<MssqlIncident> mssqlIncidentList = new ArrayList<MssqlIncident>();
		MssqlIncident mssqlIncident;
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.getDionaeaConnection(),
				IOFileUtility.readTime("mssqlTime"));

		try {
			while (rs.next()) {
				// TODO enrichmentutil geolocation and populate origin
				Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null? null: org;
				
				mssqlIncident = new MssqlIncident(rs.getString("connection_datetime").replace(' ', 'T'),
						rs.getString("remote_host"), rs.getInt("remote_port"), rs.getString("connection_protocol"),
						rs.getString("local_host"), rs.getInt("local_port"), rs.getString("connection_transport"), org,
						rs.getString("mssql_fingerprint_cltintname"), rs.getString("cmd"), rs.getString("status"),
						rs.getString("mssql_fingerprint_hostname"));
				mssqlIncidentList.add(mssqlIncident);
				IOFileUtility.writeTime("mssqlTime", rs.getString("connection_datetime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SqlUtility.closeConnection(SqlUtility.getDionaeaConnection());
		return mssqlIncidentList;
	}

}
