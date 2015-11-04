package org.aserg.model;

import java.util.Date;
import java.util.List;

import org.elasticsearch.common.joda.time.DateTime;

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
	private List<String> sipMethods;
	/**
	 * SIP user agent or tool used by the attacker.
	 */
	private String sipUserAgent;

	public SipIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, String sipCallId, List<String> sipMethods,
			String sipUserAgent) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.sipCallId = sipCallId;
		this.sipMethods = sipMethods;
		this.sipUserAgent = sipUserAgent;
	}

	public String getSipCallId() {
		return sipCallId;
	}

	public void setSipCallId(String sipCallId) {
		this.sipCallId = sipCallId;
	}

	public List<String> getSipMethods() {
		return sipMethods;
	}

	public void setSipMethods(List<String> sipMethods) {
		this.sipMethods = sipMethods;
	}

	public String getSipUserAgent() {
		return sipUserAgent;
	}

	public void setSipUserAgent(String sipUserAgent) {
		this.sipUserAgent = sipUserAgent;
	}

}