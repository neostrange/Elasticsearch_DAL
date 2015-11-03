package org.aserg.model;

import java.util.HashMap;

public class VirusTotal {
	private String virustotal_md5_hash;
	private HashMap<String, String> vtResults;

	public HashMap<String, String> getVtResults() {
		return vtResults;
	}

	public void setVtResults(HashMap<String, String> vtResults) {
		this.vtResults = vtResults;
	}

	public String getVirustotal_md5_hash() {
		return virustotal_md5_hash;
	}

	public void setVirustotal_md5_hash(String virustotal_md5_hash) {
		this.virustotal_md5_hash = virustotal_md5_hash;
	}

}
