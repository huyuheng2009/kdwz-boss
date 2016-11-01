package com.yogapay.boss.domain;

import java.util.List;

public class MonthUserYearStatic {
	private String monthSettleNo;//月结客户帐号
	private String monthName;//名称
	private String substationNo;//网点编号
	private String substationName;//网点名称
	private String courierName;//快递员姓名
	private int sumCount ;//小计
	private List<MonthUserStatic> resultList;
	public String getMonthSettleNo() {
		return monthSettleNo;
	}
	public void setMonthSettleNo(String monthSettleNo) {
		this.monthSettleNo = monthSettleNo;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getSubstationNo() {
		return substationNo;
	}
	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}
	public String getSubstationName() {
		return substationName;
	}
	public void setSubstationName(String substationName) {
		this.substationName = substationName;
	}
	public String getCourierName() {
		return courierName;
	}
	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}
	public int getSumCount() {
		return sumCount;
	}
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}
	public List<MonthUserStatic> getResultList() {
		return resultList;
	}
	public void setResultList(List<MonthUserStatic> resultList) {
		this.resultList = resultList;
	}
}
