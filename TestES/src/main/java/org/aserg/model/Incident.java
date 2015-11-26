package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main incident class which contains all the common elements found
 * in all its subtypes.
 */
public class Incident {

	private static Logger log = LoggerFactory.getLogger(Incident.class);

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
		log.trace("Create new Incident instance with dateTime [{}], srcIP [{}], dstIP [{}], dstPort [{}] ", dateTime,
				srcIP, dstIP, dstPort);
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
		log.trace("Get dateTime, returns [{}]", dateTime);
		return dateTime;
	}

	public void setDateTime(String datetime) {
		log.trace("Set dateTime to [{}]", datetime);
		this.dateTime = datetime;
	}

	public String getSrcIP() {
		log.trace("Get srcIP, returns [{}]", srcIP);
		return srcIP;
	}

	public void setSrcIP(String srcIP) {
		log.trace("Set srcIP to [{}]", srcIP);
		this.srcIP = srcIP;
	}

	public int getSrcPort() {
		log.trace("Get srcPort, returns [{}]", srcPort);
		return srcPort;
	}

	public void setSourcePort(int srcPort) {
		log.trace("Set SourcePort to [{}]", srcPort);
		this.srcPort = srcPort;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		log.trace("Set Service to [{}]", service);
		this.service = service;
	}

	public String getDstIP() {
		log.trace("Get dstIP, returns [{}]", dstIP);
		return dstIP;
	}

	public void setDestinationIP(String dstIP) {
		log.trace("Set DestinationIP to [{}]", dstIP);
		this.dstIP = dstIP;
	}

	public int getDstPort() {
		log.trace("Get dstPort, returns [{}]", dstPort);
		return dstPort;
	}

	public void setDstPort(int dstPort) {
		log.trace("Set dstPort to [{}]", dstPort);
		this.dstPort = dstPort;
	}

	public String getProtocol() {
		log.trace("Get protocol, returns [{}]", protocol);
		return protocol;
	}

	public void setProtocol(String protocol) {
		log.trace("Set protocol to [{}]", protocol);
		this.protocol = protocol;
	}

	public Origin getOrigin() {
		log.trace("Get origin, returns [{}]", origin);
		return origin;
	}

	public void setOrigin(Origin org) {
		log.trace("Set origin to [{}]", org);
		this.origin = org;
	}

}