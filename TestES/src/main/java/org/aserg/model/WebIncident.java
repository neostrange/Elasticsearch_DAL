package org.aserg.model;

import java.util.List;

/**
 * This class represents an {@code Incident} on the Application Layer.
 */
public class WebIncident extends Incident {

	/**
	 * Length of content in request.
	 */
	private int contentLength;
	/**
	 * Type of MIME content in request.
	 */
	private String contentType;
	/**
	 * HTTP method used in request, eg. GET, POST, etc.
	 */
	private String httpMethod;
	/**
	 * Path requested by attacker.
	 */
	private String pathParameter;
	/**
	 * If request is referred by an intermediary.
	 */
	private String referer;
	/**
	 * List of Modsecurity rules triggered.
	 */
	private List<WebRule> rulesList;
	/**
	 * Severity Id of the {@code Incident}.
	 */
	private int severityId;
	/**
	 * Severity status of the {@code Incident}.
	 */
	private String severityStatus;
	/**
	 * The user agent or tool used by the attacker.
	 */
	private String userAgent;

	public WebIncident(String dateTime, String srcIP, int srcPort, String service, String dstIP, int dstPort,
			String protocol, Origin org, int contentLength, String contentType, String httpMethod, String pathParameter,
			String referer, List<WebRule> rulesList, int severityId, String severityStatus, String userAgent) {
		super(dateTime, srcIP, srcPort, service, dstIP, dstPort, protocol, org);
		this.contentLength = contentLength;
		this.contentType = contentType;
		this.httpMethod = httpMethod;
		this.pathParameter = pathParameter;
		this.referer = referer;
		this.rulesList = rulesList;
		this.severityId = severityId;
		this.severityStatus = severityStatus;
		this.userAgent = userAgent;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getPathParameter() {
		return pathParameter;
	}

	public void setPathParameter(String pathParameter) {
		this.pathParameter = pathParameter;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public List<WebRule> getRulesList() {
		return rulesList;
	}

	public void setRulesList(List<WebRule> rulesList) {
		this.rulesList = rulesList;
	}

	public int getSeverityId() {
		return severityId;
	}

	public void setSeverityId(int severityId) {
		this.severityId = severityId;
	}

	public String getSeverityStatus() {
		return severityStatus;
	}

	public void setSeverityStatus(String severityStatus) {
		this.severityStatus = severityStatus;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

}