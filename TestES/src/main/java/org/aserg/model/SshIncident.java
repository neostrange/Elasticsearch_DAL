package org.aserg.model;

import java.util.List;

/**
 * This class represents an SSH {@code Incident}.
 */
public class SshIncident extends Incident {

	/**
	 * For referring to MalwareIncidents
	 */
	private String sessionId;

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
	 * The SSH tool used by the attacker.
	 */
	private String tool;

	public SshIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, String sessionId, List<Auth> authList, String endtime, List<Input> inputList,
			String tool) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		this.sessionId = sessionId;
		this.authList = authList;
		this.endtime = endtime;
		this.inputList = inputList;
		this.tool = tool;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

}