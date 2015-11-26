package org.aserg.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an {@code Incident} on the Application Layer.
 */
public class WebIncident extends Incident {

	private static Logger log = LoggerFactory.getLogger(WebIncident.class);

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
		log.trace("Create new WebIncident where contentLength [{}], contentType [{}]", contentLength, contentType);
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
		log.trace("Get contentLength, returns [{}]", contentLength);
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		log.trace("Set contentLength to [{}]", contentLength);
		this.contentLength = contentLength;
	}

	public String getContentType() {
		log.trace("Get contentType, returns [{}]", contentType);
		return contentType;
	}

	public void setContentType(String contentType) {
		log.trace("Set contentType to [{}]", contentType);
		this.contentType = contentType;
	}

	public String getHttpMethod() {
		log.trace("Get httpMethod, returns [{}]", httpMethod);
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		log.trace("Set httpMethod to [{}]", httpMethod);
		this.httpMethod = httpMethod;
	}

	public String getPathParameter() {
		log.trace("Get pathParameter, returns [{}]", pathParameter);
		return pathParameter;
	}

	public void setPathParameter(String pathParameter) {
		log.trace("Set pathParameter to [{}]", pathParameter);
		this.pathParameter = pathParameter;
	}

	public String getReferer() {
		log.trace("Get referer, returns [{}]", referer);
		return referer;
	}

	public void setReferer(String referer) {
		log.trace("Set referer to [{}]", referer);
		this.referer = referer;
	}

	public List<WebRule> getRulesList() {
		log.trace("Get rulesList, returns [{}]", rulesList);
		return rulesList;
	}

	public void setRulesList(List<WebRule> rulesList) {
		log.trace("Set rulesList to [{}]", rulesList);
		this.rulesList = rulesList;
	}

	public int getSeverityId() {
		log.trace("Get severityId, returns [{}]", severityId);
		return severityId;
	}

	public void setSeverityId(int severityId) {
		log.trace("Set severityId to [{}]", severityId);
		this.severityId = severityId;
	}

	public String getSeverityStatus() {
		log.trace("Get severityStatus, returns [{}]", severityStatus);
		return severityStatus;
	}

	public void setSeverityStatus(String severityStatus) {
		log.trace("Set severityStatus to [{}]", severityStatus);
		this.severityStatus = severityStatus;
	}

	public String getUserAgent() {
		log.trace("Get userAgent, returns [{}]", userAgent);
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		log.trace("Set userAgent to [{}]", userAgent);
		this.userAgent = userAgent;
	}

}