package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the {@code Incident} that employs the VoIP Session
 * Initiation Protocol.
 */
public class SipIncident extends Incident {

	private static Logger log = LoggerFactory.getLogger(SipIncident.class);

	/**
	 * A unique id for each attacker.
	 */
	private String sipCallId;
	/**
	 * List of SIP methods executed by the attacker.
	 */
	private String sipMethod;
	/**
	 * SIP user agent or tool used by the attacker.
	 */
	private String sipUserAgent;

	public SipIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, String sipCallId, String sipMethod, String sipUserAgent) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		log.trace("Create new SipIncident instance where sipCallId [{}], sipMethod [{}]", sipCallId, sipMethod);
		this.sipCallId = sipCallId;
		this.sipMethod = sipMethod;
		this.sipUserAgent = sipUserAgent;
	}

	public String getSipCallId() {
		log.trace("Get sipCallId, returns [{}]", sipCallId);
		return sipCallId;
	}

	public void setSipCallId(String sipCallId) {
		log.trace("Set sipCallId to [{}]", sipCallId);
		this.sipCallId = sipCallId;
	}

	public String getSipMethod() {
		log.trace("Get sipMethod, returns [{}]", sipMethod);
		return sipMethod;
	}

	public void setSipMethod(String sipMethod) {
		log.trace("Set sipMethod to [{}]", sipMethod);
		this.sipMethod = sipMethod;
	}

	public String getSipUserAgent() {
		log.trace("Get sipUserAgent, returns [{}]", sipUserAgent);
		return sipUserAgent;
	}

	public void setSipUserAgent(String sipUserAgent) {
		log.trace("Set sipUserAgent to [{}]", sipUserAgent);
		this.sipUserAgent = sipUserAgent;
	}

}