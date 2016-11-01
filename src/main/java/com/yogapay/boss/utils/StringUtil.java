package com.yogapay.boss.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.sourceforge.pinyin4j.PinyinHelper;


public class StringUtil {

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

	public static String cardNoHidden(String cardNo) {
		String start = cardNo.substring(0, 4);
		String mid = cardNo.substring(cardNo.length() - 6, cardNo.length() - 4);
		String end = cardNo.substring(cardNo.length() - 4, cardNo.length());
		return start + " .... " + mid + " " + end;

	}
	
	public static String TextHidden(String text) {
		if (text.length()<15) {
			return text ;
		}
		String start = text.substring(0, 6);
		String end = text.substring(text.length() - 3, text.length());
		return start + " ...." + end;

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

	public static boolean isEmpty(String str){
		if(str==null || str==""||str.length()<1) return true;
		return false;
	}
	
	public static boolean isEmptyWithTrim(String str){
		if(str==null || str.trim()==""||str.length()<1) return true;
		return false;
	}
	
	
	public static String getRandomString(int len) {
		String chars = "ABCDEFGHIJKLMMOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" ;
		char[] ret = new char[len] ;
		Random rr = new Random();
		 for(int i=0;i<len;i++){
	            int num = rr.nextInt(61);
	            ret[i]=chars.charAt(num) ;
	            
	        }
		return String.valueOf(ret) ;
	}
	
	public static String getRandomString(String chars,int len) {
		if (isEmpty(chars)) {
			chars = "ABCDEFGHJKLMMPQRSTUVWXYZ123456789" ;
		}
		char[] ret = new char[len] ;
		Random rr = new Random();
		 for(int i=0;i<len;i++){
	            int num = rr.nextInt(chars.length()-1);
	            ret[i]=chars.charAt(num) ;
	            
	        }
		return String.valueOf(ret) ;
	}
	
	
	public static void main(String [] args) {
		System.out.println(getRandomString(6));;
	}

	public static String valueOf(Object object) {
         if (object==null) {
			return "" ;
		}else {
			return object.toString() ;
		}
	}

	/**
	 * 判断传入参数是否为空,空字符串""或"null"
	 *
	 * @params 待判断参数
	 * @return true 空 <br>
	 * false 非空
	 */
	public static boolean isEmptyString(Object s) {
		return (s == null) || (s.toString().trim().length() == 0)
				|| s.toString().trim().equalsIgnoreCase("null");
	}
	public static String setParam(Map<String, String> values) {
		if (values == null) {
			return "";
		}
		StringBuilder params = new StringBuilder();
		for (String key : values.keySet()) {
			params.append(key)
					.append("=")
					.append(values.get(key))
					.append("&");
		}
		return params.toString();
	}
	
	
	 
    /**
	 * 根据value值获取Map中的key值
	 *
	 * @param m
	 * @param indexStr
	 * @return
	 */
	public static String getSelectKeyByString(Map<String, Object> m,
			String indexStr) {
		if ((m == null) || (indexStr == null)) {
			return null;
		}
		for (Map.Entry<String, Object> t : m.entrySet()) {
			String value = (String) t.getValue();
			if (indexStr.equals(value)) {
				return t.getKey();
			}
		}
		return "";
	}
	/**
	* @param regex
	* 正则表达式字符串
	* @param str
	* 要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	public  static boolean match(String regex, String str) {
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(str);
	return matcher.matches();
	}
}
