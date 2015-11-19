/**
 * 
 */
package org.aserg.main;

import java.util.List;

import org.aserg.dal.MalwareIncidentPopulator;
import org.aserg.dal.MssqlIncidentPopulator;
import org.aserg.model.MalwareIncident;
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
		//while (true) {
			try {
				EsUtility.pushMalwareData(malware.populate(), "incidents", "malware_incidents");
				EsUtility.pushMssqlData(mssql.populate(), "incidents", "mssql_incidents");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}

	}

}
