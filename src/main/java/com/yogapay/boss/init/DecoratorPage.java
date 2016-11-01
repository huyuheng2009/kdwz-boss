package com.yogapay.boss.init;


public class DecoratorPage {
	
	public String curDecoratorPage = "default.jsp" ;
	
	public  void setDecoratorPage(String page) {
		curDecoratorPage = page ;
	}

	public String getCurDecoratorPage() {
		return curDecoratorPage;
	}

	public void setCurDecoratorPage(String curDecoratorPage) {
		this.curDecoratorPage = curDecoratorPage;
	}

	
	
}
