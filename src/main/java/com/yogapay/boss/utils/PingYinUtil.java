/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 *
 * @author zeng
 */
public class PingYinUtil {

	/**
	 * 获取拼音
	 *
	 * @param src
	 * @return
	 */
	private static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = null;
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		// String t4 = "";
		StringBuffer t4 = new StringBuffer();
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中
					// t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后
					t4.append(t2[0]);
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串t4后
					// t4 += Character.toString(t1[i]);
					t4.append(Character.toString(t1[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4.toString().toUpperCase();
	}

	/**
	 * 获取拼音首字母
	 *
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {

		// String convert = "";
		StringBuffer convert = new StringBuffer();
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				// convert += pinyinArray[0].charAt(0);
				convert.append(pinyinArray[0].charAt(0));
			} else {
				// convert += word;
				convert.append(word);
			}
		}
		return convert.toString().toUpperCase();
	}

	public static String getPinYinHeadSingleChar(String str) {

		// String convert = "";
		StringBuffer convert = new StringBuffer();
		for (int j = 0; j < 1; j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				// convert += pinyinArray[0].charAt(0);
				convert.append(pinyinArray[0].charAt(0));
			} else {
				// convert += word;
				convert.append(word);
			}
		}
		return convert.toString().toUpperCase();
	}

	// 将字母转换成数字
	public static int letterToNum(String input) {
		int t = 0;
		for (byte b : input.getBytes()) {
			t = b;
			break;
		}
		return t;
	}

	public static void main(String[] args) {
		System.out.println(PingYinUtil.letterToNum("G"));
	}
}
