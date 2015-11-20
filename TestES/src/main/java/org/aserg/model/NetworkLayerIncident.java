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
	private String signatureClass;
	/**
	 * If ICMP event, then the type of ICMP, eg. Echo is type 8.
	 */
	private String icmpType;

	public NetworkLayerIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, int cid, int sid, String signature, String signatureClass, String icmpType) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		this.cid = cid;
		this.sid = sid;
		this.signature = signature;
		this.signatureClass = signatureClass;
		this.icmpType = icmpType;
	}

	// Without ICMP type
	public NetworkLayerIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, int cid, int sid, String signature, String signatureClass) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		this.cid = cid;
		this.sid = sid;
		this.signature = signature;
		this.signatureClass = signatureClass;
		this.icmpType = null;
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
		return signatureClass;
	}

	public void setSignature_class(String signature_class) {
		this.signatureClass = signature_class;
	}

	public String getIcmpType() {
		return icmpType;
	}

	public void setIcmpType(String icmpType) {
		this.icmpType = icmpType;
	}

}