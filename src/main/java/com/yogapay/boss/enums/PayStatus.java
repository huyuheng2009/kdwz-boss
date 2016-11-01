package com.yogapay.boss.enums;


public enum PayStatus {
	
	PAYED("111111"),
	NOPAY("000000"),
	FREIGTH("100000"),   //32
	COD("010000"),   //16
	FREIGHT_COD("110000") ;
	
  
	private String value ;
	private int intValue ;
	PayStatus(String value){
		this.value = value ;
		int v = 0 ;
	    for (int i =0; i <value.length(); i++) {
			v = (int) (v+ Integer.valueOf(String.valueOf(value.charAt(i)))*Math.pow(2, value.length()-i-1));
		}
		this.intValue = v ;
	}
	
	public String getValue() {
		return this.value ;
	}
	
	public int getIntValue() {
		return this.intValue ;
	}
	
	public boolean isPayed(String payStatus) {
		if ((this.getIntValue()&Integer.parseInt(payStatus))==this.getIntValue()) {
			return true ;
		}
		return false ;
	}
	
}
