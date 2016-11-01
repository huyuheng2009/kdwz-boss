/**
 * 项目: posboss
 * 包名: com.yogapay.boss.enums
 * 文件名: AgentLevel.java
 * 创建时间: 2014-4-22 上午10:30:41
 * 2014 支付界科技有限公司版权所有,保留所有权利;
 */
package com.yogapay.boss.enums;

/**
 * @Todo: 代理商等级
 * @Author: zhanggc
 * @Date: 2014-4-22
 */
public enum AgentLevel {
	FIRST(1),
	SECOND(2),
	THIRD(3);

	
	private int level;
	AgentLevel(int level){
		this.level = level;
	}
	
	public int getLevel(){
		return this.level;
	}

    public static AgentLevel create(int level){
        AgentLevel instant = null;
        switch(level){
            case 1:{
                instant = FIRST;
                break;}
            case 2:{
                instant = SECOND;
                break;}
            case 3:{
                instant = THIRD;
                break;}
            default:{
                instant = FIRST;
            }
        }
        return instant;

    }
}
