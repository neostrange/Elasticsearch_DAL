package org.aserg.model;

/**
 * This is the main incident class which contains all the common elements found
 * in all its subtypes.
 */
public class Incident {

	/**
	 * The incident datetime.
	 */
	private String dateTime;
	/**
	 * IP of the source that the attack originated from.
	 */
	private String srcIP;
	/**
	 * Source port where the attack originated from.
	 */
	private int srcPort;
	/**
	 * The type of service.
	 */
	private String service;
	/**
	 * This is the destination IP address of the targeted sensor.
	 */
	private String dstIP;
	/**
	 * This is the destination port of the targeted sensor.
	 */
	private int dstPort;
	/**
	 * The transport layer protocol (tcp, udp, etc).
	 */
	private String protocol;
	/**
	 * The information regarding the origin of the attack.
	 */
	private Origin origin;

	public Incident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org) {
		super();
		this.dateTime = dateTime;
		this.srcIP = srcIP;
		this.srcPort = srcPort;
		this.service = service;
		this.dstIP = dstIP;
		this.dstPort = dstPort;
		this.protocol = protocol;
		this.origin = org;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String datetime) {
		this.dateTime = datetime;
	}

	public String getSourceIP() {
		return srcIP;
	}

	public void setSourceIP(String localHost) {
		this.srcIP = localHost;
	}

	public int getSourcePort() {
		return srcPort;
	}

	public void setSourcePort(int localPort) {
		this.srcPort = localPort;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDestinationIP() {
		return dstIP;
	}

	public void setDestinationIP(String remoteHost) {
		this.dstIP = remoteHost;
	}

	public int getDestinationPort() {
		return dstPort;
	}

	public void setDestinationPort(int remotePort) {
		this.dstPort = remotePort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin org) {
		this.origin = org;
	}

}