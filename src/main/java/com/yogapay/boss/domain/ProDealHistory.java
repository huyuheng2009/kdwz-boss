package com.yogapay.boss.domain;

public class ProDealHistory {
	
	private Integer id ; 
	
	private Integer proId ;
	
	private String dealText ;
	
	private String couierNo ;
	
	private String substationNo ;
	
	private String dealer ;
	
	private String createTime ;
	
	private String dealStatus ;
	
	private String reson ;
	
	private String resonText ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getDealText() {
		return dealText;
	}

	public void setDealText(String dealText) {
		this.dealText = dealText;
	}

	public String getCouierNo() {
		return couierNo;
	}

	public void setCouierNo(String couierNo) {
		this.couierNo = couierNo;
	}

	public String getSubstationNo() {
		return substationNo;
	}

	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getReson() {
		return reson;
	}

	public void setReson(String reson) {
		this.reson = reson;
	}

	public String getResonText() {
		return resonText;
	}

	public void setResonText(String resonText) {
		this.resonText = resonText;
	}

	
}
