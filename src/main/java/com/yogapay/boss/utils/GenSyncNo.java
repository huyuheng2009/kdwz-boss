package com.yogapay.boss.utils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 生成POS终端号
 * 
 * @author donjek
 * 
 */
public class GenSyncNo implements ApplicationContextAware {
	private static GenSyncNo instance = null;
	private static ApplicationContext applicationContext; // Spring应用上下文环境

	private GenSyncNo() {

	}

	public static synchronized GenSyncNo getInstance() {
		if (null == instance) {
			instance = new GenSyncNo();
		}
		return instance;
	}

	// 商户编号
	public synchronized String getNextMerchantNo() throws SQLException {
		return getNext("merchant_no_seq", "10000");
	}

	// 终端号
	public synchronized String getNextTerminalNo() throws SQLException {
		return getNext("terminal_no_seq", "10000000");
	}

	private synchronized String getNext(String seqName, String defValue)
			throws SQLException {
		Dao dao = applicationContext.getBean(Dao.class);
		Map<String, Object> map = dao.findFirst("select nextval('" + seqName
				+ "') as t");

		BigInteger v = new BigInteger(defValue);
		Object t = map.get("t");
		if (null != t) {
			String src = t.toString();
			v = new BigInteger(src);
			v.add(new BigInteger("1"));
		}

		return v.toString();
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// TODO Auto-generated method stub
		applicationContext = ctx;
	}

}
