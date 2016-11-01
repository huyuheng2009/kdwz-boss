package com.yogapay.boss.domain;

public class OrderTrack {
	
	private int id ;
	
	private String orderNo ; 
	
	private String orderTime ; 
	
	private String context ;
	
	private String completed ;
	
	private String preNo ;
	
	private String preType ;
	
	private String curNo ;
	
	private String curType ;
	
	private String nextNo ;
	
	private String nextType ; 
	
	private String orderStatus ;
	
	private int parentId ;
	
	private String isLast ;
	
	private String scanIno ;
	
	private String scanIname ;
	
	private String scanOno ;
	
	private String scanOname ;
	
	private String opname ;

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

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}
	
	public String getPreNo() {
		return preNo;
	}

	public void setPreNo(String preNo) {
		this.preNo = preNo;
	}

	public String getPreType() {
		return preType;
	}

	public void setPreType(String preType) {
		this.preType = preType;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getNextNo() {
		return nextNo;
	}

	public void setNextNo(String nextNo) {
		this.nextNo = nextNo;
	}

	public String getNextType() {
		return nextType;
	}

	public void setNextType(String nextType) {
		this.nextType = nextType;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getIsLast() {
		return isLast;
	}

	public void setIsLast(String isLast) {
		this.isLast = isLast;
	}

	public String getScanIno() {
		return scanIno;
	}

	public void setScanIno(String scanIno) {
		this.scanIno = scanIno;
	}

	public String getScanIname() {
		return scanIname;
	}

	public void setScanIname(String scanIname) {
		this.scanIname = scanIname;
	}

	public String getScanOno() {
		return scanOno;
	}

	public void setScanOno(String scanOno) {
		this.scanOno = scanOno;
	}

	public String getScanOname() {
		return scanOname;
	}

	public void setScanOname(String scanOname) {
		this.scanOname = scanOname;
	}

	public String getOpname() {
		return opname;
	}

	public void setOpname(String opname) {
		this.opname = opname;
	}
	
}
