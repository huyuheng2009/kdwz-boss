package com.yogapay.boss.domain;

import java.math.BigDecimal;

public class CourierSalayPay {
	private long id;
	private String crateTime;
	private String courierNo;
	private String costMonth;
	private BigDecimal costAmout;
	private int courierTcWay;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCrateTime() {
		return crateTime;
	}
	public void setCrateTime(String crateTime) {
		this.crateTime = crateTime;
	}
	public String getCourierNo() {
		return courierNo;
	}
	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}
	public String getCostMonth() {
		return costMonth;
	}
	public void setCostMonth(String costMonth) {
		this.costMonth = costMonth;
	}
	public BigDecimal getCostAmout() {
		return costAmout;
	}
	public void setCostAmout(BigDecimal costAmout) {
		this.costAmout = costAmout;
	}
	public int getCourierTcWay() {
		return courierTcWay;
	}
	public void setCourierTcWay(int courierTcWay) {
		this.courierTcWay = courierTcWay;
	}
	
	
}
