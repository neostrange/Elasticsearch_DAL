package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.MssqlIncident;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;

public class MssqlIncidentPopulator {
	
	List<MssqlIncident> mssqlIncidentList = new ArrayList<MssqlIncident>();
	MssqlIncident mssqlIncident;

	public MssqlIncidentPopulator() {
			// TODO Auto-generated constructor stub
	}

	public List<MssqlIncident> populate() {
		
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MSSQL_INCIDENT_QUERY, SqlUtility.dionaeaConnection,
				IOFileUtility.readTime("mssqlTime"));
		
		
		try {
			while (rs.next()) {
				mssqlIncident = new MssqlIncident(rs.getString("connection_datetime").replace(' ', 'T'), 
						rs.getString("local_host"),rs.getInt("local_port"), 
						rs.getString("connection_protocol"), rs.getString("remote_host"),
						rs.getInt("remote_port"), rs.getString("connection_transport"),
						EnrichmentUtility.getCountry(rs.getString("remote_host")), 
						rs.getString("mssql_fingerprint_cltintname"),rs.getString("cmd"), 
						rs.getString("status"), rs.getString("mssql_fingerprint_hostname"));
				mssqlIncidentList.add(mssqlIncident);
				IOFileUtility.writeTime("mssqlTime", rs.getString("connection_datetime"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mssqlIncidentList;
	}

}
