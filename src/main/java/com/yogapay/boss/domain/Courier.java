package com.yogapay.boss.domain;

import java.util.Date;

public class Courier {
	private Integer id;
	private String userName; //用户名
	private String passWord; //密码
	private String realName; //姓名
	private String substationNo; //分站编号
	private String idCard; //身份证号
	private String phone; //电话号
	private String courierNo; //快递员编号
	private String queueName; //队列名
	private String headImage; //头像地址
	private String registTime; //注册时间
	private String createOperator ;
	private String innerNo ;
	private String innerPhone ;
	private String sarea ;
	private int status ;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSubstationNo() {
		return substationNo;
	}
	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCourierNo() {
		return courierNo;
	}
	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public String getInnerNo() {
		return innerNo;
	}
	public void setInnerNo(String innerNo) {
		this.innerNo = innerNo;
	}
	public String getInnerPhone() {
		return innerPhone;
	}
	public void setInnerPhone(String innerPhone) {
		this.innerPhone = innerPhone;
	}
	public String getSarea() {
		return sarea;
	}
	public void setSarea(String sarea) {
		this.sarea = sarea;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
