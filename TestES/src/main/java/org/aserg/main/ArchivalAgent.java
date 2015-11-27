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
import org.aserg.utility.EnrichmentUtility;
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
		try{
			while (true) {
				if (IOFileUtility.readProperty("malwareState", BASE_PATH).equals("on")) {

					log.warn("Malware State Property is [ON]");
					MalwareIncidentPopulator malware = new MalwareIncidentPopulator();
					EsUtility.pushMalwareData(malware.populate(), "incident", "MalwareIncident");

				} else
					log.warn("Malware State Property is [OFF]");

				if (IOFileUtility.readProperty("mssqlState", BASE_PATH).equals("on")) {

					log.warn("Mssql State Property is [ON]");
					MssqlIncidentPopulator mssql = new MssqlIncidentPopulator();
					EsUtility.pushMssqlData(mssql.populate(), "incident", "MssqlIncident");

				} else
					log.warn("Mssql State Property is [OFF]");

				if (IOFileUtility.readProperty("mysqlState", BASE_PATH).equals("on")) {

					log.warn("Mysql State Property is [ON]");
					MysqlIncidentPopulator mysql = new MysqlIncidentPopulator();
					EsUtility.pushMysqlData(mysql.populate(), "incident", "MysqlIncident");

				} else
					log.warn("Mssql State Property is [OFF]");

				if (IOFileUtility.readProperty("sipState", BASE_PATH).equals("on")) {

					log.warn("Sip State Property is [ON]");
					SipIncidentPopulator sip = new SipIncidentPopulator();
					EsUtility.pushSipData(sip.populate(), "incident", "SipIncident");

				} else
					log.warn("Sip State Property is [OFF]");

				if (IOFileUtility.readProperty("sshState", BASE_PATH).equals("on")) {

					log.warn("Ssh State Property is [ON]");
					SshIncidentPopulator ssh = new SshIncidentPopulator();
					EsUtility.pushSshData(ssh.populate(), "incident", "SshIncident");

				} else
					log.warn("Ssh State Property is [OFF]");

				if (IOFileUtility.readProperty("sshMalwareState", BASE_PATH).equals("on")) {

					log.warn("Ssh Malware State Property is [ON]");
					MalwareIncidentPopulator malware = new MalwareIncidentPopulator();
					EsUtility.pushMalwareData(malware.populateSsh(), "incident", "MalwareIncident");

				} else
					log.warn("Ssh Malware State Property is [OFF]");

				if (IOFileUtility.readProperty("webState", BASE_PATH).equals("on")) {

					log.warn("Web State Property is [ON]");
					WebIncidentPopulator web = new WebIncidentPopulator();
					EsUtility.pushWebData(web.populate(), "incident", "WebIncident");

				} else
					log.warn("Web State Property is [OFF]");

				if (IOFileUtility.readProperty("networkState", BASE_PATH).equals("on")) {

					log.warn("Network State Property is [ON]");
					NetworkLayerIncidentPopulator network = new NetworkLayerIncidentPopulator();
					EsUtility.pushNetworkData(network.populate(), "incident", "NetworkLayerIncident");

				} else
					log.warn("Network State Property is [OFF]");

			}
			//EnrichmentUtility.getOrigin("91.244.37.15");
		} catch (InterruptedException e){
			log.error("Error Occured while pushing data to Elastic Search from Archival Agent. ", e);
		}
	}

}
