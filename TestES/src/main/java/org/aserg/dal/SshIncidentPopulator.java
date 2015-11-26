package org.aserg.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.aserg.model.Auth;
import org.aserg.model.Input;
import org.aserg.model.Origin;
import org.aserg.model.SshIncident;
import org.aserg.utility.EnrichmentUtility;
import org.aserg.utility.IOFileUtility;
import org.aserg.utility.SqlUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SshIncidentPopulator {

	private static Logger log = LoggerFactory.getLogger(SshIncidentPopulator.class);

	public List<SshIncident> populate() {
		log.info("Initiating SshIncident Population");
		SshIncident ssh = null;
		List<SshIncident> sshIncidentList = new ArrayList<SshIncident>();
		List<Auth> authList = null;
		Auth auth = null;
		List<Input> inputList = null;
		Input input = null;
		String lastFetchTime = IOFileUtility.readProperty("sshTime", IOFileUtility.STATE_PATH);
		String lastTime = lastFetchTime ;
		log.info("Run query to fetch ssh records");
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.SSH_INCIDENT_QUERY, SqlUtility.getKippoConnection(),
				lastFetchTime);
		String prev = null;
		String localhost = IOFileUtility.readProperty("HOST", IOFileUtility.ARCHIVAL_PATH);
		boolean authenticated = false;
		try {
			log.info("SSH traversing started");
			while (rs.next()) {
				
				Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null ? null : org;
				if (rs.getString("input_timestamp") != null) {
					input = new Input(rs.getString("input"),
							Boolean.valueOf(rs.getInt("input_success") == 1 ? "true" : "false"),
							rs.getString("input_timestamp").replace(' ', 'T'));
				}

				if (rs.getString("order_id").equals(prev)) {
					log.debug("SshIncident same as previous, session [{}] ", prev);
					// if prev auth attempt in the session was successful
					if (inputList != null) {
						inputList.add(input);
						ssh.setInputList(inputList);

					}
					if (rs.getString("auth_timestamp") != null && !authenticated) {
						auth = new Auth(rs.getString("username"), rs.getString("password"),
								Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false"),
								rs.getString("auth_timestamp").replace(' ', 'T'));
						authList.add(auth);
						ssh.setAuthList(authList);
						authenticated = Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false");
					}

				} else {

					if (ssh != null) {
						if (authList != null) {
							ssh.setAuthList(authList);
							log.debug("Added authentication list to SshIncident, attempts [{}] ", authList.size());
						}
						if (inputList != null) {
							ssh.setInputList(inputList);
							log.debug("Added input list to SshIncident, attempts [{}] ", inputList.size());

						}
						sshIncidentList.add(ssh);
						log.debug("Added SshIncident, session [{}] to list ", prev);
						authList = null;
						inputList = null;
						authenticated = false;
					}

					String stime = rs.getString("connection_datetime").replace(' ', 'T');
					String etime = rs.getString("endtime") != null ? rs.getString("endtime").replace(' ', 'T') : null;
					String client = rs.getString("version");
					String remotehost = rs.getString("remote_host");
					ssh = new SshIncident(stime, remotehost, 22, "sshd",
							localhost, 22, "tcp", org,
							rs.getString("order_id"), null, etime, null, client);
					lastTime = rs.getString("connection_datetime");
					// in case there are auth attempts
					if (rs.getString("auth_timestamp") != null) {
						auth = new Auth(rs.getString("username"), rs.getString("password"),
								Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false"),
								rs.getString("auth_timestamp").replace(' ', 'T'));
						authList = new ArrayList<Auth>();
						authList.add(auth);
						ssh.setAuthList(authList);
						authenticated = Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false");

						// in case auth is successful, there may or may not be
						// an input
						if (input != null) {
							inputList = new ArrayList<Input>();
							inputList.add(input);
							ssh.setInputList(inputList);
							log.debug("Added input list to SshIncident, attempts [{}] ", inputList.size());

						}
					}
				}

				if (rs.isLast() && !sshIncidentList.contains(ssh)) {
					System.out.println("Last Record");
					sshIncidentList.add(ssh);
				}
				prev = rs.getString("order_id");
				input = null;
				auth = null;

			}
		} catch (SQLException e) {
			log.error("Error occurred while trying to traverse through ssh records ", e);
		}
		IOFileUtility.writeProperty("sshTime", lastTime,
				IOFileUtility.STATE_PATH);
		SqlUtility.closeConnection(SqlUtility.getKippoConnection());
		log.debug("Number of new ssh incidents [{}], since last fetched at [{}] ", sshIncidentList.size(),
				lastFetchTime);
		log.info("SshIncident Population Successful");
		return sshIncidentList;
	}

}
