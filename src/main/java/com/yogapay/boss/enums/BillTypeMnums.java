package com.yogapay.boss.enums;
//
public enum BillTypeMnums {
	A("S","充值"),
	B("Z","冲账"),
	C("ZZ","中转"),
	D("PJ","派件"),
	E("CASH","现金"),
	F("POS","转账"),
	G("BOSS","系统"),
	H("USER","用户");
	private String type;
	private String text;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	BillTypeMnums(String type,String text){
		this.type=type;
		this.text=text;
	}
	public static String getText(String type){
			for(BillTypeMnums c :BillTypeMnums.values()){
				if(type.equals(c.getType())){
					return c.getText();
				}			
			}
			return "";					
	}

	
	
}
