/**
 * 
 */
package org.aserg.main;

import org.aserg.dal.MalwareIncidentPopulator;
import org.aserg.dal.MssqlIncidentPopulator;
import org.aserg.dal.MysqlIncidentPopulator;
import org.aserg.dal.NetworkLayerIncidentPopulator;
import org.aserg.dal.SipIncidentPopulator;
import org.aserg.utility.EsUtility;

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
		//while (true) {
			try {
				EsUtility.pushMalwareData(malware.populate(), "incidents", "malwareIncidents");
				EsUtility.pushMssqlData(mssql.populate(), "incidents", "mssqlIncidents");
				EsUtility.pushMysqlData(mysql.populate(), "incidents", "mysqlIncidents");
//				EsUtility.pushNetworkData(network.populate(), "incidents", "networkLayerIncidents");
				EsUtility.pushSipData(sip.populate(), "incidents", "sipIncidents");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}

	}

}
