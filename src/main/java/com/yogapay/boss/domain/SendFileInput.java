package com.yogapay.boss.domain;

public class SendFileInput {
private String userNo;
private String orderNo;
private String sendTime;
private String sendName;
private String sendCourierNo;
private String sendSubNo;
public String getUserNo() {
	return userNo;
}
public void setUserNo(String userNo) {
	this.userNo = userNo;
}
public String getOrderNo() {
	return orderNo;
}
public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
}
public String getSendTime() {
	return sendTime;
}
public void setSendTime(String sendTime) {
	this.sendTime = sendTime;
}
public String getSendName() {
	return sendName;
}
public void setSendName(String sendName) {
	this.sendName = sendName;
}
public String getSendCourierNo() {
	return sendCourierNo;
}
public void setSendCourierNo(String sendCourierNo) {
	this.sendCourierNo = sendCourierNo;
}
public String getSendSubNo() {
	return sendSubNo;
}
public void setSendSubNo(String sendSubNo) {
	this.sendSubNo = sendSubNo;
}

}
