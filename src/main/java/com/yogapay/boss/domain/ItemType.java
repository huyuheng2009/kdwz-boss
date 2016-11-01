package com.yogapay.boss.domain;

public class ItemType {

	private Integer id ;
	
	private String itemText ;
	
	private String note ;
	
	private Integer defaultItem ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getDefaultItem() {
		return defaultItem;
	}

	public void setDefaultItem(Integer defaultItem) {
		this.defaultItem = defaultItem;
	}
	
	
	
}
