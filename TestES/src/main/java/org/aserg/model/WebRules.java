package org.aserg.model;


/**
 * Singleton for describing the Modsecurity rule triggered by the
 * {@code WebIncident}.
 */
public class WebRules {

	/**
	 * Category of the triggered rule.
	 */
	private String ruleCategory;
	/**
	 *  Message of the triggered rule.
	 */
	private String ruleMessage;
		
	public WebRules(String ruleCategory, String ruleMessage) {
		super();
		this.ruleCategory = ruleCategory;
		this.ruleMessage = ruleMessage;
	}

	public String getRuleCategory() {
		return ruleCategory;
	}

	public void setRuleCategory(String ruleCategory) {
		this.ruleCategory = ruleCategory;
	}

	public String getRuleMessage() {
		return ruleMessage;
	}

	public void setRuleMessage(String ruleMessage) {
		this.ruleMessage = ruleMessage;
	}
	
	

}