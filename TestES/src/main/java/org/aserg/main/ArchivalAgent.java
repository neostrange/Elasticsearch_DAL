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
import org.aserg.dal.WebIncidentPopulatror;
import org.aserg.utility.EsUtility;
import org.aserg.utility.IOFileUtility;

/**
 * @author Waseem
 *
 */
public class ArchivalAgent {

	/**
	 * 
	 */
	public ArchivalAgent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MalwareIncidentPopulator malware = new MalwareIncidentPopulator();
		MssqlIncidentPopulator mssql = new MssqlIncidentPopulator(); 
		MysqlIncidentPopulator mysql = new MysqlIncidentPopulator();
		NetworkLayerIncidentPopulator network = new NetworkLayerIncidentPopulator();
		SipIncidentPopulator sip = new SipIncidentPopulator();
		WebIncidentPopulatror web = new WebIncidentPopulatror();
		SshIncidentPopulator ssh = new SshIncidentPopulator();
		while (true) {
			try {
				if(IOFileUtility.readTime("malwareState").equals("on")){

					EsUtility.pushMalwareData(malware.populate(), "incidents", "malwareIncidents");
				}
				if(IOFileUtility.readTime("mssqlState").equals("on")){

					EsUtility.pushMssqlData(mssql.populate(), "incidents", "mssqlIncidents");
				}
				if(IOFileUtility.readTime("mysqlState").equals("on")){

					EsUtility.pushMysqlData(mysql.populate(), "incidents", "mysqlIncidents");
				}
				if(IOFileUtility.readTime("sipState").equals("on")){

					EsUtility.pushSipData(sip.populate(), "incidents", "sipIncidents");
				}
				if(IOFileUtility.readTime("sshState").equals("on")){

					EsUtility.pushSshData(ssh.populate(), "incidents", "sshIncidents");
				}
				if(IOFileUtility.readTime("sshMalwareState").equals("on")){

					EsUtility.pushMalwareData(malware.populateSsh(), "incidents", "malwareIncidents");
				}
				if(IOFileUtility.readTime("webState").equals("on")){

					EsUtility.pushWebData(web.populate(), "incidents", "webIncidents");
				}
				if(IOFileUtility.readTime("networkState").equals("on")){

					EsUtility.pushNetworkData(network.populate(), "incidents", "networkLayerIncidents");
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
