package org.aserg.es;

public class VirusTotal{
public String virustotal_md5_hash;
public VirusTotalScan vts;
	
	public VirusTotalScan getVts() {
	return vts;
}

public void setVts(VirusTotalScan vts) {
	this.vts = vts;
}

	public String getVirustotal_md5_hash() {
		return virustotal_md5_hash;
	}

	public void setVirustotal_md5_hash(String virustotal_md5_hash) {
		this.virustotal_md5_hash = virustotal_md5_hash;
	}

}
