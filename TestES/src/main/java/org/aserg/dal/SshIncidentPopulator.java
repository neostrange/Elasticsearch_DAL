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

public class SshIncidentPopulator {
	
	public List<SshIncident> populate() {
		
		SshIncident ssh = null;
		List<SshIncident> sshIncidentList = new ArrayList<SshIncident>();
		List<Auth> authList = null;
		Auth auth = null;
		List<Input> inputList = null;
		Input input = null;
		ResultSet rs = SqlUtility.getResultSet(SqlUtility.SSH_INCIDENT_QUERY, SqlUtility.getKippoConnection(),
				IOFileUtility.readTime("sshTime"));
		String prev = null;
		boolean authenticated = false;
		try {
			while (rs.next()) {

				Origin org = EnrichmentUtility.getOrigin(rs.getString("remote_host"));
				org = org == null? null: org;
				if (rs.getString("input_timestamp") != null) {
					input = new Input(rs.getString("input"),
							Boolean.valueOf(rs.getInt("input_success") == 1 ? "true" : "false"),
							rs.getString("input_timestamp").replace(' ', 'T'));

				}
				if (rs.getString("auth_timestamp") != null) {
					auth = new Auth(rs.getString("username"), rs.getString("password"),
							Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false"),
							rs.getString("auth_timestamp").replace(' ', 'T'));
				}

				if (rs.getString("id").equals(prev)) {

					// if prev auth attempt in the session was successful
					if (inputList != null) {
						inputList.add(input);
						ssh.setInputList(inputList);

					}
					if (!authenticated) {
						authList.add(auth);
						ssh.setAuthList(authList);
						authenticated = Boolean.valueOf(rs.getInt("auth_success") == 1 ? "true" : "false");
					}

				} else {

					if (ssh != null) {
						if (authList != null) {
							ssh.setAuthList(authList);
						}
						if (inputList != null) {
							ssh.setInputList(inputList);
						}
						sshIncidentList.add(ssh);
						authList = null;
						inputList = null;
						authenticated = false;
					}

					String stime = rs.getString("connection_datetime").replace(' ', 'T');
					String etime = rs.getString("endtime") != null ? rs.getString("endtime").replace(' ', 'T') : null;
					String client = rs.getString("version");
					String remotehost = rs.getString("remote_host");
					ssh = new SshIncident(stime, remotehost, 22, "sshd", rs.getString("local_host"), 22, "tcp", org, rs.getString("order_id"), null, etime, null, client);
					IOFileUtility.writeTime("sshTime", rs.getString("connection_datetime"));
					// in case there are auth attempts
					if (auth != null) {
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
						}
					}
				}

				if (rs.isLast() && !sshIncidentList.contains(ssh)) {
					sshIncidentList.add(ssh);
				}

				prev = rs.getString("id");
				input = null;
				auth = null;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
