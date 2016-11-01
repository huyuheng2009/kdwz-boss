package com.yogapay.boss.enums;

public enum CheckTrackStatus {
    A("SIGNING","派件"),
    B("SEND","出站"),
    C("REV","到站"),
    D("","全部"),
    E("SIGNED","签收"),
    F("PRO","问题件"),
    G("TAKEING","收件");
   
   private String pay;
   private String index;
    private CheckTrackStatus(String pay,String index){
	this.pay= pay;
	this.index = index;
}
  public static String getType(String  index){
	  for(CheckTrackStatus c:CheckTrackStatus.values()){
		  if(index.equals(c.getIndex())){
			  return c.getPay();
		  }
	  }
	  return "";  
  }
  public static String getIndex(String pay){
	  for(CheckTrackStatus c:CheckTrackStatus.values()){
		  if(pay.equals(c.getPay())){
			  return c.getIndex();
		  }		  
	  }
	  return "";
  }
   
public String getPay() {
	return pay;
}
public void setPay(String pay) {
	this.pay = pay;
}
public String getIndex() {
	return index;
}
public void setIndex(String index) {
	this.index = index;
}
   
}
