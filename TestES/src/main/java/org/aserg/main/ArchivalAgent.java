/**
 * 
 */
package org.aserg.main;

import org.aserg.dal.MalwareIncidentPopulator;
import org.aserg.dal.MssqlIncidentPopulator;
import org.aserg.dal.MysqlIncidentPopulator;
import org.aserg.dal.NetworkLayerIncidentPopulator;
import org.aserg.dal.SipIncidentPopulator;
import org.aserg.dal.SshIncidentPopulator;
import org.aserg.dal.WebIncidentPopulator;
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Waseem
 *
 */
public class ArchivalAgent {

	private static Logger log = LoggerFactory.getLogger(ArchivalAgent.class);

	private static final String BASE_PATH = System.getProperty("user.dir") + "/config/state.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		log.info("Archival Agent Initialized");
		EsUtility.initBulkProcessor();
		while (true) {
			if (IOFileUtility.readProperty("malwareState", BASE_PATH).equals("on")) {

				log.warn("Malware State Property is [ON]");
				MalwareIncidentPopulator.pushMalwareIncidents("incident", "MalwareIncident");

			} else
				log.warn("Malware State Property is [OFF]");

			if (IOFileUtility.readProperty("mssqlState", BASE_PATH).equals("on")) {

				log.warn("Mssql State Property is [ON]");
				MssqlIncidentPopulator.pushMssqlIncidents("incident", "MssqlIncident");

			} else
				log.warn("Mssql State Property is [OFF]");

			if (IOFileUtility.readProperty("mysqlState", BASE_PATH).equals("on")) {

				log.warn("Mysql State Property is [ON]");
				MysqlIncidentPopulator.pushMysqlIncidents("incident", "MysqlIncident");

			} else
				log.warn("Mssql State Property is [OFF]");

			if (IOFileUtility.readProperty("sipState", BASE_PATH).equals("on")) {

				log.warn("Sip State Property is [ON]");
				SipIncidentPopulator.pushSipIncidents("incident", "SipIncident");

			} else
				log.warn("Sip State Property is [OFF]");

			if (IOFileUtility.readProperty("sshState", BASE_PATH).equals("on")) {

				log.warn("Ssh State Property is [ON]");
				SshIncidentPopulator.pushSshIncidents("incident", "SshIncident");

			} else
				log.warn("Ssh State Property is [OFF]");

			if (IOFileUtility.readProperty("sshMalwareState", BASE_PATH).equals("on")) {

				log.warn("Ssh Malware State Property is [ON]");
				MalwareIncidentPopulator.pushSSHMalwareIncidents("incident", "MalwareIncident");

			} else
				log.warn("Ssh Malware State Property is [OFF]");

			if (IOFileUtility.readProperty("webState", BASE_PATH).equals("on")) {

				log.warn("Web State Property is [ON]");
				WebIncidentPopulator.pushWebIncidents("incident", "WebIncident");

			} else
				log.warn("Web State Property is [OFF]");

			if (IOFileUtility.readProperty("networkState", BASE_PATH).equals("on")) {

				log.warn("Network State Property is [ON]");
				NetworkLayerIncidentPopulator.pushNetworkLayerIncidents("incident", "NetworkLayerIncident");

			} else
				log.warn("Network State Property is [OFF]");

		}
	}

}
