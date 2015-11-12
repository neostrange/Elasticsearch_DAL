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
	 * The sensor host that was attacked.
	 */
	private String localHost;
	/**
	 * The target port that was attacked.
	 */
	private int localPort;
	/**
	 * The type of service.
	 */
	private String protocol;
	/**
	 * This is the source IP address who launched an attack.
	 */
	private String remoteHost;
	/**
	 * The port through which attack was launched.
	 */
	private int remotePort;
	/**
	 * The transport protocol (tcp, udp, etc).
	 */
	private String transport;
	/**
	 * The country from where the attack was originated.
	 */
	private String country;

	public Incident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country) {
		this.dateTime = dateTime;
		this.localHost = localHost;
		this.localPort = localPort;
		this.protocol = protocol;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.transport = transport;
		this.setCountry(country);
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String datetime) {
		this.dateTime = datetime;
	}

	public String getLocalHost() {
		return localHost;
	}

	public void setLocalHost(String localHost) {
		this.localHost = localHost;
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}