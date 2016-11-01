package com.yogapay.boss.domain;

import java.util.Date;
/**
 *月结客户每月统计表
 * */
public class MonthUserStatic {
private int id;
private String column2;//月结号
private int column3;//月总发件数
private float column4;//月应收金额
private String column5;//统计时间
private String createTime;//录入时间
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getColumn2() {
	return column2;
}
public void setColumn2(String column2) {
	this.column2 = column2;
}
public int getColumn3() {
	return column3;
}
public void setColumn3(int column3) {
	this.column3 = column3;
}
public float getColumn4() {
	return column4;
}
public void setColumn4(float column4) {
	this.column4 = column4;
}
public String getColumn5() {
	return column5;
}
public void setColumn5(String column5) {
	this.column5 = column5;
}
public String getCreateTime() {
	return createTime;
}
public void setCreateTime(String createTime) {
	this.createTime = createTime;
}
@Override
public String toString() {
	return "MonthUserStatic [id=" + id + ", column2=" + column2 + ", column3="
			+ column3 + ", column4=" + column4 + ", column5=" + column5
			+ ", createTime=" + createTime + "]";
}

}
