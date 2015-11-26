package org.aserg.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an SSH {@code Incident}.
 */
public class SshIncident extends Incident {
	
	private static Logger log = LoggerFactory.getLogger(SshIncident.class);

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
	private String endTime;
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
		log.trace("Create new SshIncident where sessionId [{}]", sessionId);
		this.sessionId = sessionId;
		this.authList = authList;
		this.endTime = endtime;
		this.inputList = inputList;
		this.tool = tool;
	}

	public String getSessionId() {
		log.trace("Get sessionId, returns [{}]", sessionId);
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		log.trace("Set sessionId to [{}]", sessionId);
		this.sessionId = sessionId;
	}

	public List<Auth> getAuthList() {
		log.trace("Get authList, returns [{}]", authList);
		return authList;
	}

	public void setAuthList(List<Auth> authList) {
		log.trace("Set authList to [{}]", authList);
		this.authList = authList;
	}

	public String getEndTime() {
		log.trace("Get endTime, returns [{}]", endTime);
		return endTime;
	}

	public void setEndTime(String endtime) {
		log.trace("Set endTime to [{}]", endtime);
		this.endTime = endtime;
	}

	public List<Input> getInputList() {
		log.trace("Get InputList, returns [{}]", inputList);
		return inputList;
	}

	public void setInputList(List<Input> inputList) {
		log.trace("Set inputList to [{}]", inputList);
		this.inputList = inputList;
	}

	public String getTool() {
		log.trace("Get tool, returns [{}]", tool);
		return tool;
	}

	public void setTool(String tool) {
		log.trace("Set tool to [{}]", tool);
		this.tool = tool;
	}

}