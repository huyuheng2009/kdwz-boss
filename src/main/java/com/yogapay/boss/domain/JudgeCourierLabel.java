package com.yogapay.boss.domain;

public class JudgeCourierLabel {
	private long id;
	private String courierNo;
	private long labelId;
	private int times;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCourierNo() {
		return courierNo;
	}
	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}
	public long getLabelId() {
		return labelId;
	}
	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	
	
}
