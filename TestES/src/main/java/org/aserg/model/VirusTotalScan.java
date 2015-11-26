package org.aserg.model;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class contains the virustotal scan reference and individual scanner
 * results.
 */
public class VirusTotalScan {
	
	private static Logger log = LoggerFactory.getLogger(VirusTotalScan.class);

	/**
	 * The URL for the full virustotal analysis result.
	 */
	private String permalink;
	/**
	 * HashMap scanner result of analysis.
	 */
	private HashMap<String, String> VTscanResults;

	public VirusTotalScan(String permalink, HashMap<String, String> vTscanResults) {
		super();
		log.trace("Create new VirusTotalScan instance with permalink [{}]", permalink);
		this.permalink = permalink;
		VTscanResults = vTscanResults;
	}

	public String getPermalink() {
		log.trace("Get permalink, returns [{}]", permalink);
		return permalink;
	}

	public void setPermalink(String permalink) {
		log.trace("Set permalink to [{}]", permalink);
		this.permalink = permalink;
	}

	public HashMap<String, String> getVTscanResults() {
		log.trace("Get VTscanResults, returns [{}]", VTscanResults);
		return VTscanResults;
	}

	public void setVTscanResults(HashMap<String, String> vTscanResultList) {
		log.trace("Set VTscanResults to [{}]", VTscanResults);
		VTscanResults = vTscanResultList;
	}

}