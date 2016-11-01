package com.yogapay.boss.domain;

import java.util.Date;

public class AppVersion {
	
	private int id ;
	
	private int appCode ;
	
	private String platform ;
	
	private String version ; 
	
	private String publishTime ;
	
	private String address ;
	
	private String downloadAddress ;
	
	private String ipaAddress ;
	
	private int status ;
	
	private int mupdate ;

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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getMupdate() {
		return mupdate;
	}

	public void setMupdate(int mupdate) {
		this.mupdate = mupdate;
	}

	public String getDownloadAddress() {
		return downloadAddress;
	}

	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}

	public String getIpaAddress() {
		return ipaAddress;
	}

	public void setIpaAddress(String ipaAddress) {
		this.ipaAddress = ipaAddress;
	}
	
	

}
