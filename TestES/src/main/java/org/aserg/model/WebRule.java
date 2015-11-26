package org.aserg.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton for describing the Modsecurity rule triggered by the
 * {@code WebIncident}.
 */
public class WebRule {

	private static Logger log = LoggerFactory.getLogger(WebRule.class);

	/**
	 * Category of the triggered rule.
	 */
	private String ruleCategory;
	/**
	 * Message of the triggered rule.
	 */
	private String ruleMessage;

	public WebRule(String ruleCategory, String ruleMessage) {
		super();
		log.trace("Create new WebRule where ruleMessage [{}]", ruleMessage);
		this.ruleCategory = ruleCategory;
		this.ruleMessage = ruleMessage;
	}

	public String getRuleCategory() {
		log.trace("Get ruleCategory, returns [{}]", ruleCategory);
		return ruleCategory;
	}

	public void setRuleCategory(String ruleCategory) {
		log.trace("Set ruleCategory to [{}]", ruleCategory);
		this.ruleCategory = ruleCategory;
	}

	public String getRuleMessage() {
		log.trace("Get ruleMessage, returns [{}]", ruleMessage);
		return ruleMessage;
	}

	public void setRuleMessage(String ruleMessage) {
		log.trace("Set ruleMessage to [{}]", ruleMessage);
		this.ruleMessage = ruleMessage;
	}

}