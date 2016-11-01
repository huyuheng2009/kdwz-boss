package com.yogapay.boss.domain;

public class SystemPunish {
  
	private Integer id ;
	
	private String punishText ;
	
	private String ruleText ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPunishText() {
		return punishText;
	}

	public void setPunishText(String punishText) {
		this.punishText = punishText;
	}

	public String getRuleText() {
		return ruleText;
	}

	public void setRuleText(String ruleText) {
		this.ruleText = ruleText;
	}

	
}
