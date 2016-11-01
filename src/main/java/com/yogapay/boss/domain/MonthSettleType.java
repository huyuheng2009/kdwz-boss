package com.yogapay.boss.domain;

public class MonthSettleType {
	
	private Integer id  ;
	
	private String name ;
	
	private Integer discount ;
	
	private String discountText ;
	
	private String note ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getDiscountText() {
		return discountText;
	}

	public void setDiscountText(String discountText) {
		this.discountText = discountText;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
}
