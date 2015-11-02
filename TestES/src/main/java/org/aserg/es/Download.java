package org.aserg.es;

public class Download extends VirusTotal {
	String download_url;
    public VirusTotal vtr;
	public VirusTotal getVtr() {
		return vtr;
	}

	public void setVtr(VirusTotal vtr) {
		this.vtr = vtr;
	}

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
}
