package com.yogapay.boss.domain;

public class JudgeDetail {
	private long id;
	private String courierNo;
	private int star;
	private String orderNo;
	private String createTime;
	private String userNo;
	private String labelTxt;
	private String comments;
	private String lgcOrderNo;
	private int takeOrSend;
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
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getLabelTxt() {
		return labelTxt;
	}
	public void setLabelTxt(String labelTxt) {
		this.labelTxt = labelTxt;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getLgcOrderNo() {
		return lgcOrderNo;
	}
	public void setLgcOrderNo(String lgcOrderNo) {
		this.lgcOrderNo = lgcOrderNo;
	}
	public int getTakeOrSend() {
		return takeOrSend;
	}
	public void setTakeOrSend(int takeOrSend) {
		this.takeOrSend = takeOrSend;
	}
	
}
