/**
 * 项目: posboss
 * 包名: com.yogapay.boss.enums
 * 文件名: RealNameStatus.java
 * 创建时间: 2014-4-16 下午4:24:14
 * 2014 支付界科技有限公司版权所有,保留所有权利;
 */
package com.yogapay.boss.enums;

/**
 * @Todo: 实名认证状态
 * @Author: zhanggc
 * @Date: 2014-4-16
 */
public enum RealNameStatus {
	UNSUBMIT(),	//未提交
	SUBMITTED(), //已提交
	PASSED(), //通过
	UNPASS(); //未通过
}
