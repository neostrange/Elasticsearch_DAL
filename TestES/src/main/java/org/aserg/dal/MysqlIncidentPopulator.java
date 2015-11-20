package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.MysqlCommand;
import org.aserg.model.MysqlIncident;
import org.aserg.model.Origin;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;

public class MysqlIncidentPopulator {

	MysqlIncident mysqlIncident = null;
	List<MysqlIncident> mysqlIncidentList = new ArrayList<MysqlIncident>();
	MysqlCommand mysqlCommand = null;
	List<MysqlCommand> mysqlCommandList = null;

	public MysqlIncidentPopulator() {
		// TODO Auto-generated constructor stub
	}

	public List<MysqlIncident> populate() {

		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MYSQL_INCIDENT_QUERY, SqlUtility.dionaeaConnection,
				IOFileUtility.readTime("mysqlTime"));

		String prev = null;
		try {
			while (rs.next()) {
				mysqlCommand = new MysqlCommand(rs.getString("mysql_command_args.mysql_command_arg_data"),
						rs.getString("mysql_command_ops.mysql_command_op_name"));
				// in case of new connection
				if (!rs.getString("cmc.connection").equals(prev)) {
					if (mysqlIncident != null)
						mysqlIncidentList.add(mysqlIncident);
					mysqlCommandList = new ArrayList<MysqlCommand>();
					Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
					org = org == null? null: org;
					mysqlIncident = new MysqlIncident(rs.getString("connection_datetime").replace(' ', 'T'),
							rs.getString("cmc.remote_host"), rs.getInt("cmc.remote_port"),
							rs.getString("cmc.connection_protocol"), rs.getString("cmc.local_host"),
							rs.getInt("cmc.local_port"), rs.getString("cmc.connection_transport"), org, null);
					prev = rs.getString("cmc.connection");
					IOFileUtility.writeTime("mysqlTime", rs.getString("connection_datetime"));
					// add mysql command
					mysqlCommandList.add(mysqlCommand);
					mysqlIncident.setMysqlCommands(mysqlCommandList);
				}
				// if connection same as prev, just add mysql command
				else {
					mysqlCommandList.add(mysqlCommand);
					mysqlIncident.setMysqlCommands(mysqlCommandList);
					// for the last one
					if (!rs.next()) {
						mysqlIncidentList.add(mysqlIncident);
					}

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mysqlIncidentList;
	}

}
