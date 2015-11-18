package org.aserg.model;


/**
 * Network Layer {@code Incident} where snort alert is triggered. Contains cid
 * for referring to the PCAP information in future.
 */
public class NetworkLayerIncident extends Incident {

	/**
	 * Id for referring to the full PCAP information from Snorby/Snort.
	 */
	private int cid;
	/**
	 * Id for referring to the sensor on which the attack was received.
	 */
	private int sid;
	/**
	 * Snort signature for this particular {@code Incident}.
	 */
	private String signature;
	/**
	 * Snort signature classification.
	 */
	private String signature_class;
	/**
	 * If ICMP event, then the type of ICMP, eg. Echo is type 8.
	 */
	private String icmpType;
	
	/**
	 * 
	 * @param dateTime
	 * @param localHost
	 * @param localPort
	 * @param protocol
	 * @param remoteHost
	 * @param remotePort
	 * @param transport
	 * @param country
	 * @param cid
	 * @param sid
	 * @param signature
	 * @param signature_class
	 */
	
	public NetworkLayerIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, int cid, int sid, String signature,
			String signature_class) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.cid = cid;
		this.sid = sid;
		this.signature = signature;
		this.signature_class = signature_class;
		this.icmpType = null;
	}
	
	// with icmp
	public NetworkLayerIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, int cid, int sid, String signature,
			String signature_class, String icmpType) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.cid = cid;
		this.sid = sid;
		this.signature = signature;
		this.signature_class = signature_class;
		this.icmpType = icmpType;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature_class() {
		return signature_class;
	}

	public void setSignature_class(String signature_class) {
		this.signature_class = signature_class;
	}

}