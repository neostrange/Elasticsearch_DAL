/**
 * 
 */
package org.aserg.main;

import java.util.List;

import org.aserg.dal.MalwareIncidentPopulator;
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
		List<MalwareIncident> mp = new MalwareIncidentPopulator().populate();
		try {
			EsUtility.pushMalwareData(mp, "incidents", "malware_incidents");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
