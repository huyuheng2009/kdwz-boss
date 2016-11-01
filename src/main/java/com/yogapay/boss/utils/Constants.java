package com.yogapay.boss.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;

import com.sun.mail.util.BASE64DecoderStream;
import com.yogapay.boss.domain.BossUser;
import com.yogapay.boss.domain.LgcConfig;

public class Constants {
	// 签到，查询余额，消费，撤销，冲正，退货，批结，签退，回响，初始化
	public static String DEF_PERMISSION = "1111101111";

	// 普通商户扣率标志
	public static String RATE_SELF = "SELF";

	// 商户编号前缀
	//public static String MERCHANT_START = "2704400";
	public static String MERCHANT_START = "802";
	

	// 代理商结算报表存放文件位置/var/posboss/settle
	public static String AGENT_SETTLE_PATH = "/var/posboss/settle/";
	public static String EMAIL_SMTP = "smtp.exmail.qq.com";
	public static String EMAIL_USERNSME = "hyh@yogapay.com";
	public static String EMAIL_PASSWORD = "eW9nYXBheTIwMTQ="; 
	public static String EMAIL_ADDRESS = "hyh@yogapay.com";

	public static BossUser getUser() {
		return (BossUser) SecurityUtils.getSubject().getSession()
				.getAttribute("user");
	}
	
	public static LgcConfig getLgcConfig() {
		return (LgcConfig) SecurityUtils.getSubject().getSession()
				.getAttribute("lgcConfig");
	}
	
	public static String appNames() {
		return (String) SecurityUtils.getSubject().getSession()
				.getAttribute("appNames");
	}
}
