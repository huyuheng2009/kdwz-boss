package com.yogapay.boss.domain;

public class OrderSubstation {
  
	private Integer id ;
	
	private Integer orderId ;
	
	private String substationNo ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getSubstationNo() {
		return substationNo;
	}

	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}
	
	
	
}
