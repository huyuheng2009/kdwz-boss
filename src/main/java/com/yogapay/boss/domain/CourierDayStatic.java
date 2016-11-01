package com.yogapay.boss.domain;

import java.util.Date;
/**
 * 快递员每日统计表
 * */
public class CourierDayStatic {
private int id;
private String courierNo;//快递员编号
private int revCount;//收件数
private int sendCount;//派件数
private float fcount;//应收现金
private float mcount;//应收月结
private float ccount;//应收代收款
private float acount;//应收总额
private String staticDate;//统计日期
private String createTime;//录入时间
@Override
public String toString() {
	return "SubstationDayStatic [id=" + id + ", courierNo=" + courierNo
			+ ", revCount=" + revCount + ", sendCount=" + sendCount
			+ ", fcount=" + fcount + ", mcount=" + mcount + ", ccount="
			+ ccount + ", acount=" + acount + ", staticDate=" + staticDate
			+ ", createTime=" + createTime + "]";
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCourierNo() {
	return courierNo;
}
public void setCourierNo(String courierNo) {
	this.courierNo = courierNo;
}
public int getRevCount() {
	return revCount;
}
public void setRevCount(int revCount) {
	this.revCount = revCount;
}
public int getSendCount() {
	return sendCount;
}
public void setSendCount(int sendCount) {
	this.sendCount = sendCount;
}
public float getFcount() {
	return fcount;
}
public void setFcount(float fcount) {
	this.fcount = fcount;
}
public float getMcount() {
	return mcount;
}
public void setMcount(float mcount) {
	this.mcount = mcount;
}
public float getCcount() {
	return ccount;
}
public void setCcount(float ccount) {
	this.ccount = ccount;
}
public float getAcount() {
	return acount;
}
public void setAcount(float acount) {
	this.acount = acount;
}
public String getStaticDate() {
	return staticDate;
}
public void setStaticDate(String staticDate) {
	this.staticDate = staticDate;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
}
