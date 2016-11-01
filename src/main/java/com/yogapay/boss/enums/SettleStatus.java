
package com.yogapay.boss.enums;

/**
 * @Todo: 对账状态
 * @Author: zhanggc
 */
public enum SettleStatus {
	INITITE(0),//未对账
	PASS(1),//对账平
	UNPASS(2);//对账不平
	
	private int status;
	SettleStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}
}
