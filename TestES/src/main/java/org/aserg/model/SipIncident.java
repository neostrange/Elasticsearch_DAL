package org.aserg.model;


/**
 * This class represents the {@code Incident} that employs the VoIP Session
 * Initiation Protocol.
 */
public class SipIncident extends Incident {

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
		this.sipCallId = sipCallId;
		this.sipMethod = sipMethod;
		this.sipUserAgent = sipUserAgent;
	}

	public String getSipCallId() {
		return sipCallId;
	}

	public void setSipCallId(String sipCallId) {
		this.sipCallId = sipCallId;
	}

	public String getSipMethod() {
		return sipMethod;
	}

	public void setSipMethod(String sipMethod) {
		this.sipMethod = sipMethod;
	}

	public String getSipUserAgent() {
		return sipUserAgent;
	}

	public void setSipUserAgent(String sipUserAgent) {
		this.sipUserAgent = sipUserAgent;
	}

}