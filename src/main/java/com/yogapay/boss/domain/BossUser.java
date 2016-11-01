package com.yogapay.boss.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户信息表
 * 
 * @author dj
 * 
 */
public class BossUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201608031L;

	private Long id;
	/**
	 * 登录名称
	 */
	private String userName;
	/**
	 * 真是名字
	 */
	private String realName;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 1启用 0停用
	 */
	private String status;
	/**
	 * MD5加密密码
	 */
	private String password;
	/**
	 * 操作员
	 */
	private String lgcNo ;
	private String createOperator;
	private Integer failTimes;
	private String updateTime;// 修改密码时间
	private String createTime;
	private String substationNo;
	private String sno;
	private Integer inputTimes ;
	
	private String layoutPage ;  //后台布局皮肤
	
	private String soid ;  //当前签收录入的订单ids
	
	private String tips ;  //后台tips提醒，Y表示提示,N表示不提示
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public Integer getFailTimes() {
		return failTimes;
	}
	public void setFailTimes(Integer failTimes) {
		this.failTimes = failTimes;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLgcNo() {
		return lgcNo;
	}
	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}
	public String getSubstationNo1() {
		
		return substationNo;
	}
	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}
	public String getLayoutPage() {
		return layoutPage;
	}
	public void setLayoutPage(String layoutPage) {
		this.layoutPage = layoutPage;
	}
	public Integer getInputTimes() {
		return inputTimes;
	}
	public void setInputTimes(Integer inputTimes) {
		this.inputTimes = inputTimes;
	}
	public String getSoid() {
		return soid;
	}
	public void setSoid(String soid) {
		this.soid = soid;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getSno() {
		return sno;
	}
	public void setSno(String sno) {
		this.sno = sno;
	}
	
	
	
}
