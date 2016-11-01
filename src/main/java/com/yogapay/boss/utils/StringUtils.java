/**
 * 项目: message
 * 包名: com.zfj.message.commons.utils
 * 文件名: StringUtils.java
 * 创建时间: 2014-3-3
 * 2014 支付界科技有限公司版权所有,保留所有权利;
 */
package com.yogapay.boss.utils;

import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @Todo: 字符串处理工具
 * @Author: zhanggc
 * @Date: 2014-3-3
 */
public class StringUtils {
	
	public static boolean isEmpty(String str){
		if(str==null || str==""||str.length()<1) return true;
		return false;
	}
	
	public static boolean isEmptyWithTrim(String str){
		if(str==null || str.trim()==""||str.length()<1) return true;
		return false;
	}
	

	/**
	 * 字符串左补0
	 * */
	public static String stringFillLeftZero(String str, int len) {
		if (str.length() < len) {
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len - str.length(); i++)
				sb.append('0');
			sb.append(str);
			return new String(sb);
		} else
			return str;
	}
	
	/**
	 * tostring
	 * @param object
	 * @return
	 */
	public static String nullString(Object object) {
		return object==null?"":object.toString().trim() ;
	}
	
	/**
	 * tostring
	 * @param object
	 * @return
	 */
	public static String stringNull(Object object) {
		return object==null?null:object.toString().trim() ;
	}
	
	/**
	 * tostring
	 * @param object
	 * @return
	 */
	public static String nullStringtoRound(Object object) {		     
		   NumberFormat nf = NumberFormat.getNumberInstance();   
	         nf.setMaximumFractionDigits(2);   
		return object==null?"0":nf.format(object) ;
	}
	
	/**
	 * tostring
	 * @param object
	 * @return
	 */
	public static String nullString(Object object,String def) {
		if(object==null || object.toString().length()<1) 
			return def ;
		return object==null?def:object.toString() ;
	}

	public static String cardNoHidden(String cardNo) {
		String start = cardNo.substring(0, 4);
		String mid = cardNo.substring(cardNo.length() - 6, cardNo.length() - 4);
		String end = cardNo.substring(cardNo.length() - 4, cardNo.length());
		return start + " .... " + mid + " " + end;

	}

	/**
	 * 
	 * @param obj
	 * @return String
	 * @obj==null,或obj是空字符串，就返回参数ifEmptyThen，否则返回obj.toString。
	 */

	public static String ifEmptyThen(Object obj, String ifEmptyThen) {
		String ret = "";
		if (obj == null || String.valueOf(obj) == "") {
			ret = ifEmptyThen;
		} else {
			ret = obj.toString();
		}
		return ret;
	}
	/**
	 * tostring
	 * @param object
	 * @return
	 */
	public static String nullStringtoZero(Object object) {
		return object==null?"0":object.toString().trim() ;
	}
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			// 提取汉字的首字母
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	// 过滤非法字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		Pattern p = null;
		Matcher m = null;
		String value = null;
		// 去掉<>标签及其之间的内容
		p = Pattern.compile("(<[^>]*>)");
		m = p.matcher(str);
		String temp = str;
		// 下面的while循环式进行循环匹配替换，把找到的所有
		// 符合匹配规则的字串都替换为你想替换的内容
		while (m.find()) {
			value = m.group(0);
			temp = temp.replace(value, "");
		}
		return temp;
	}
	
	public static String getRandomString(int len,boolean allNO) {
		String chars = "ABCDEFGHIJKLMMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" ;
		if (allNO) {
		chars = "0123456789" ;
		}
		int charLen = chars.length() ;
		char[] ret = new char[len] ;
		Random rr = new Random();
		 for(int i=0;i<len;i++){
	            int num = rr.nextInt(charLen);
	            ret[i]=chars.charAt(num) ;
	            
	        }
		return String.valueOf(ret) ;
	}

}
