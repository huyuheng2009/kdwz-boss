package com.yogapay.boss.enums;

public enum Check {
    A("1","寄方付"),
    B("2","收方付"),
    C("MONTH","月结"),
    D("CASH","现金"),
    E("WEIXIN","微信"),
    F("HUIYUAN","会员");
  
      
   private String pay;
   private String index;
    private Check(String pay,String index){
	this.pay= pay;
	this.index = index;
}
  public static String getType(String  index){
	  for(Check c:Check.values()){
		  if(index.equals(c.getIndex())){
			  return c.getPay();
		  }
	  }
	  return "";  
  }
  public static String getIndex(String pay){
	   for(Check c:Check.values()){
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
