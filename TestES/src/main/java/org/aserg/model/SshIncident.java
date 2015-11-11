package org.aserg.model;

import java.util.Date;
import java.util.List;

/**
 * This class represents an SSH {@code Incident}.
 */
public class SshIncident extends Incident {

	/**
	 * The list of Authentication attempts made by the attacker.
	 */
	private List<Auth> authList;
	/**
	 * The time attacker's SSH session ended.
	 */
	private String endtime;
	/**
	 * The list of commands the attacker attempted to execute.
	 */
	private List<Input> inputList;
	/**
	 * The list of {@code MalwareIncident}, in case the attacker downloaded any
	 * malwares during SSH session.
	 */
	private List<MalwareIncident> malwareIncidents;
	/**
	 * The SSH tool used by the attacker.
	 */
	private String tool;

	public SshIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, List<Auth> authList, String endtime,
			List<Input> inputList, List<MalwareIncident> malwareIncidents, String tool) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.authList = authList;
		this.endtime = endtime;
		this.inputList = inputList;
		this.malwareIncidents = malwareIncidents;
		this.tool = tool;
	}
	
	public SshIncident(String dateTime, String localHost, int localPort, String protocol, String remoteHost,
			int remotePort, String transport, String country, List<Auth> authList, String endtime,
			List<Input> inputList, String tool) {
		super(dateTime, localHost, localPort, protocol, remoteHost, remotePort, transport, country);
		this.authList = authList;
		this.endtime = endtime;
		this.inputList = inputList;
		this.malwareIncidents = null;
		this.tool = tool;
	}

	public List<Auth> getAuthList() {
		return authList;
	}

	public void setAuthList(List<Auth> authList) {
		this.authList = authList;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public List<Input> getInputList() {
		return inputList;
	}

	public void setInputList(List<Input> inputList) {
		this.inputList = inputList;
	}

	public List<MalwareIncident> getMalwareIncidents() {
		return malwareIncidents;
	}

	public void setMalwareIncidents(List<MalwareIncident> malwareIncidents) {
		this.malwareIncidents = malwareIncidents;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

}