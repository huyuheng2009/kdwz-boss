package com.yogapay.boss.domain;

import java.util.Date;

public class CodMonthCount {
	
	
	private Integer id ;
	
	private String codNo ;
	
	private  String settleDate ;
	
	private String settleTime ;
	
	private String codPrice ;
	
	private String returnPrice ;
	
	private String settled ;
	
	private String printed ;
	
	private Date createTime ;

   private String discount ;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodNo() {
		return codNo;
	}

	public void setCodNo(String codNo) {
		this.codNo = codNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getSettleTime() {
		return settleTime;
	}

	public void setSettleTime(String settleTime) {
		this.settleTime = settleTime;
	}

	public String getCodPrice() {
		return codPrice;
	}

	public void setCodPrice(String codPrice) {
		this.codPrice = codPrice;
	}

	public String getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(String returnPrice) {
		this.returnPrice = returnPrice;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}

	public String getPrinted() {
		return printed;
	}

	public void setPrinted(String printed) {
		this.printed = printed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	

}
