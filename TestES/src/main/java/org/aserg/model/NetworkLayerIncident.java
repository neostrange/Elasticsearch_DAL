package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Network Layer {@code Incident} where snort alert is triggered. Contains cid
 * for referring to the PCAP information in future.
 */
public class NetworkLayerIncident extends Incident {
	
	private static Logger log = LoggerFactory.getLogger(NetworkLayerIncident.class);

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
		log.trace("Create new NetworkLayerIncident where cid [{}], sid [{}]", cid, sid);
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
		log.trace("Create new NetworkLayerIncident where cid [{}], sid [{}]", cid, sid);
		this.cid = cid;
		this.sid = sid;
		this.signature = signature;
		this.signatureClass = signatureClass;
		this.icmpType = null;
	}

	public int getCid() {
		log.trace("Get cid, returns [{}]", cid);
		return cid;
	}

	public void setCid(int cid) {
		log.trace("Set cid to [{}]", cid);
		this.cid = cid;
	}

	public int getSid() {
		log.trace("Get sid, returns [{}]", sid);
		return sid;
	}

	public void setSid(int sid) {
		log.trace("Set sid to [{}]", sid);
		this.sid = sid;
	}

	public String getSignature() {
		log.trace("Get signature, returns [{}]", signature);
		return signature;
	}

	public void setSignature(String signature) {
		log.trace("Set signature to [{}]", signature);
		this.signature = signature;
	}

	public String getSignatureClass() {
		log.trace("Get signatureClass, returns [{}]", signatureClass);
		return signatureClass;
	}

	public void setSignatureClass(String signature_class) {
		log.trace("Set signatureClass to [{}]", signature_class);
		this.signatureClass = signature_class;
	}

	public String getIcmpType() {
		log.trace("Get icmpType, returns [{}]", icmpType);
		return icmpType;
	}

	public void setIcmpType(String icmpType) {
		log.trace("Set icmpType to [{}]", icmpType);
		this.icmpType = icmpType;
	}

}