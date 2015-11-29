package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.MysqlCommand;
import org.aserg.model.MysqlIncident;
import org.aserg.model.Origin;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MysqlIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(MysqlIncidentPopulator.class);

	public static void pushMysqlIncidents(String index, String type) {
		log.info("Initiating MysqlIncident Population");

		MysqlIncident mysqlIncident = null;
		MysqlCommand mysqlCommand = null;
		List<MysqlCommand> mysqlCommandList = null;
		Origin org;
		String lastFetchTime = IOFileUtility.readProperty("mysqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mysql records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MYSQL_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		String prev = null;
		String remotehost = null;
		String localhost = null;
		int count = 0;
		
		try {
			while (rs.next()) {

				if (rs.getString("cmc.remote_host").contains(":"))
					remotehost = rs.getString("cmc.remote_host").split("f:")[1];
				else
					remotehost = rs.getString("cmc.remote_host");
		
				if (rs.getString("cmc.local_host").contains(":"))
					localhost = rs.getString("cmc.local_host").split("f:")[1];
				else
					localhost = rs.getString("cmc.local_host");
				
				mysqlCommand = new MysqlCommand(rs.getString("mysql_command_args.mysql_command_arg_data"),
						rs.getString("mysql_command_ops.mysql_command_op_name"));
				// in case of new connection
				if (!rs.getString("cmc.connection").equals(prev)) {
					if (mysqlIncident != null) {
						EsUtility.pushDocument(new Gson().toJson(mysqlIncident), index, type);
						count++;
						log.debug("Added MysqlIncident to BulkProcessor, connection [{}], commands [{}]",
								rs.getString("cmc.connection"), mysqlIncident.getMysqlCommands().size());

					}
					mysqlCommandList = new ArrayList<MysqlCommand>();
					org = EnrichmentUtility.getOrigin(remotehost);
					org = org == null ? null : org;
					lastFetchTime = rs.getString("connection_datetime");
					mysqlIncident = new MysqlIncident(lastFetchTime.replace(' ', 'T'), remotehost,
							rs.getInt("cmc.remote_port"), rs.getString("cmc.connection_protocol"),
							localhost, rs.getInt("cmc.local_port"),
							rs.getString("cmc.connection_transport"), org, null);
					prev = rs.getString("cmc.connection");
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
						EsUtility.pushDocument(new Gson().toJson(mysqlIncident), index, type);
						count++;
					}

				}
			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through mysql records", e);
		}

		IOFileUtility.writeProperty("mysqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		log.debug("Number of new mysql incidents [{}], since last fetched at [{}] ", count, lastFetchTime);
		log.info("MysqlIncident Population Successful");
	}

	public List<MysqlIncident> populate() {
		log.info("Initiating MysqlIncident Population");

		MysqlIncident mysqlIncident = null;
		List<MysqlIncident> mysqlIncidentList = new ArrayList<MysqlIncident>();
		MysqlCommand mysqlCommand = null;
		List<MysqlCommand> mysqlCommandList = null;
		Origin org;
		String lastFetchTime = IOFileUtility.readProperty("mysqlTime", IOFileUtility.STATE_PATH);
		log.debug("Run query to fetch mysql records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.MYSQL_INCIDENT_QUERY, SqlUtility.getSqliteConnection(),
				lastFetchTime);
		String prev = null;
		String remotehost = null;
		String localhost = null;
		try {
			while (rs.next()) {

				if (rs.getString("cmc.remote_host").contains(":"))
					remotehost = rs.getString("cmc.remote_host").split("f:")[1];
				else
					remotehost = rs.getString("cmc.remote_host");
				
				if (rs.getString("cmc.local_host").contains(":"))
					localhost = rs.getString("cmc.local_host").split("f:")[1];
				else
					localhost = rs.getString("cmc.local_host");
				
				mysqlCommand = new MysqlCommand(rs.getString("mysql_command_args.mysql_command_arg_data"),
						rs.getString("mysql_command_ops.mysql_command_op_name"));
				// in case of new connection
				if (!rs.getString("cmc.connection").equals(prev)) {
					if (mysqlIncident != null) {
						mysqlIncidentList.add(mysqlIncident);
						log.debug("Added MysqlIncident to list, connection [{}], commands [{}]",
								rs.getString("cmc.connection"), mysqlIncident.getMysqlCommands().size());

					}
					mysqlCommandList = new ArrayList<MysqlCommand>();
					org = EnrichmentUtility.getOrigin(remotehost);
					org = org == null ? null : org;
					lastFetchTime = rs.getString("connection_datetime");
					mysqlIncident = new MysqlIncident(lastFetchTime.replace(' ', 'T'), remotehost,
							rs.getInt("cmc.remote_port"), rs.getString("cmc.connection_protocol"),
							localhost, rs.getInt("cmc.local_port"),
							rs.getString("cmc.connection_transport"), org, null);
					prev = rs.getString("cmc.connection");
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
			log.error("Error occurred while trying to traverse through mysql records", e);
		}

		IOFileUtility.writeProperty("mysqlTime", lastFetchTime, IOFileUtility.STATE_PATH);
		SqlUtility.closeDbInstances(SqlUtility.getSqliteConnection());
		log.debug("Number of new mysql incidents [{}], since last fetched at [{}] ", mysqlIncidentList.size(),
				lastFetchTime);
		log.info("MysqlIncident Population Successful");
		return mysqlIncidentList;
	}

}
