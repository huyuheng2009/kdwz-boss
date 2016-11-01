package com.yogapay.boss.domain;

public class ProOrderReason {
      
	private Integer id  ;
	
	private String reasonNo ;
	
	private String context ;
	
	private String dealed ;
	
	private String note ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReasonNo() {
		return reasonNo;
	}

	public void setReasonNo(String reasonNo) {
		this.reasonNo = reasonNo;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDealed() {
		return dealed;
	}

	public void setDealed(String dealed) {
		this.dealed = dealed;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
