package com.yogapay.boss.domain;

public class ForwardOrder {
	
	private int id ;
	
	private String orderNo ;
	
	private String forwardType ;
	
	private String curNo ;
	
	private String curLgcOrderNo ;
	
    private String forwardTime ;
	
	private String scanNo ;
	
	private String scanName ;
	
	private String ioLgcOrderNo ;
	
	private String ioName ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getForwardType() {
		return forwardType;
	}

	public void setForwardType(String forwardType) {
		this.forwardType = forwardType;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCurLgcOrderNo() {
		return curLgcOrderNo;
	}

	public void setCurLgcOrderNo(String curLgcOrderNo) {
		this.curLgcOrderNo = curLgcOrderNo;
	}

	public String getForwardTime() {
		return forwardTime;
	}

	public void setForwardTime(String forwardTime) {
		this.forwardTime = forwardTime;
	}

	public String getScanNo() {
		return scanNo;
	}

	public void setScanNo(String scanNo) {
		this.scanNo = scanNo;
	}

	public String getScanName() {
		return scanName;
	}

	public void setScanName(String scanName) {
		this.scanName = scanName;
	}

	public String getIoLgcOrderNo() {
		return ioLgcOrderNo;
	}

	public void setIoLgcOrderNo(String ioLgcOrderNo) {
		this.ioLgcOrderNo = ioLgcOrderNo;
	}

	public String getIoName() {
		return ioName;
	}

	public void setIoName(String ioName) {
		this.ioName = ioName;
	}

	
}
