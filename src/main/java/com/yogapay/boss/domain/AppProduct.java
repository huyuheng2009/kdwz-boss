package com.yogapay.boss.domain;

public class AppProduct {
	
	private int id ;
	
	private int appCode ; 
	
	private String appName ;
	
	private int status ;
	
	private String bname ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppCode() {
		return appCode;
	}

	public void setAppCode(int appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}
	

}
