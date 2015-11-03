package org.aserg.model;

public class Download{
	String download_url;
    public VirusTotal vtResult;
	public VirusTotal getVtResult() {
		return vtResult;
	}

	public void setVtResult(VirusTotal vtr) {
		this.vtResult = vtr;
	}

	public String getDownload_url() {
		return download_url;
	}

	public void setDownload_url(String download_url) {
		this.download_url = download_url;
	}
}
