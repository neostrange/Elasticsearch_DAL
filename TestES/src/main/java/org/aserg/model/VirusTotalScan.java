package org.aserg.model;

import java.util.HashMap;

/**
 * This class contains the virustotal scan reference and individual scanner
 * results.
 */
public class VirusTotalScan {

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
		this.permalink = permalink;
		VTscanResults = vTscanResults;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public HashMap<String, String> getVTscanResults() {
		return VTscanResults;
	}

	public void setVTscanResults(HashMap<String, String> vTscanResultList) {
		VTscanResults = vTscanResultList;
	}

}