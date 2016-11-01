package com.yogapay.boss.utils;

import java.security.MessageDigest;

/**
 * @Todo: SHA 安全散列算法
 * @Author: zhanggc
 * @Date: 2014-4-15
 */
public class SHA {
	/**
	 * @Todo: SHA1 加密
	 * @param source
	 * @return
	 */
	public static String SHA1Encode(String source) {
		String result = null;
		try {
			result = new String(source);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			result = byte2hexString(md.digest(result.getBytes()));
		} catch (Exception ex) {
		}
		return result.toLowerCase();
	}
	
	public static String SHA1Encode1(String source) {
		String result = null;
		try {
			result = new String(source);
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			result = byte2hexString(md.digest(result.getBytes("utf-8")));
		} catch (Exception ex) {
		}
		return result;
	}

	public static final String byte2hexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buf.toString().toUpperCase();
	}
	
	public static void main(String[] args){
		System.out.println(SHA1Encode("15820724996yogapayHFT1PTSD"));
	}
}
