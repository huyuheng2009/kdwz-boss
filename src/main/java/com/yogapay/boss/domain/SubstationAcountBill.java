package com.yogapay.boss.domain;

import java.math.BigDecimal;
import java.util.Date;

public class SubstationAcountBill {
	private long id;
	private String substationNo;
	private Date createTime;
	private String lgcOrderNo;
	private Date sendTime;
	private String type;
	private String payType;
	private BigDecimal beforeBalance;
	private BigDecimal useBalance;
	private BigDecimal afterBalance;
	private String source;
	private String note;
	private String operater;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSubstationNo() {
		return substationNo;
	}
	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLgcOrderNo() {
		return lgcOrderNo;
	}
	public void setLgcOrderNo(String lgcOrderNo) {
		this.lgcOrderNo = lgcOrderNo;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public BigDecimal getBeforeBalance() {
		return beforeBalance;
	}
	public void setBeforeBalance(BigDecimal beforeBalance) {
		this.beforeBalance = beforeBalance;
	}
	public BigDecimal getUseBalance() {
		return useBalance;
	}
	public void setUseBalance(BigDecimal useBalance) {
		this.useBalance = useBalance;
	}
	public BigDecimal getAfterBalance() {
		return afterBalance;
	}
	public void setAfterBalance(BigDecimal afterBalance) {
		this.afterBalance = afterBalance;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOperater() {
		return operater;
	}
	public void setOperater(String operater) {
		this.operater = operater;
	}
	
	
	
}
