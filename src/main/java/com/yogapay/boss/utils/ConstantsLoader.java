package com.yogapay.boss.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jpos.iso.IF_CHAR;

/**
 * @Todo: 应用变量加载
 * @Author: zhanggc
 * @Date: 2014-3-20
 */
public class ConstantsLoader {
	private static Properties p;
	private static Properties p1;
	public static enum Path{
		
		APPPATH("/config/config.properties");
		private String value;
		private String value1;
		Path(String value){
			this.value = value;
			this.value1 = value;
			if (StringUtils.isEmptyWithTrim(System.getenv("spring.profiles.active"))) {
				//this.value = "/config/config_override.properties";
				System.out.println(System.getProperty("user.dir")+"----------------------user.dir");
				this.value1 = System.getProperty("user.dir")+"/config_pro.properties" ;
			}
		}
		public String Value(){
			return this.value;
		}
		public String Value1(){
			return this.value1;
		}
	}

	static{
		 p = new Properties();
		 p1 = new Properties();
		 try {
			p.load(ConstantsLoader.class.getResourceAsStream(Path.APPPATH.Value()));
			 if (StringUtils.isEmptyWithTrim(System.getenv("spring.profiles.active"))) {
				  p1.load(new FileInputStream(Path.APPPATH.Value1()));
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void init(Path path){
		 p = new Properties();
		 p1 = new Properties();
		try {
			p.load(ConstantsLoader.class.getResourceAsStream(path.Value()));
			if (StringUtils.isEmptyWithTrim(System.getenv("spring.profiles.active"))) {
			  p1.load(new FileInputStream(path.Value1()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		if(p.size()==0) return null;
		if (StringUtils.isEmptyWithTrim(System.getenv("spring.profiles.active"))) {
			if (p1.get(key)!=null) {
				return p1.get(key).toString();
			}else {
				return p.get(key)==null?null:p.get(key).toString();
			}
		}else {
			return p.get(key)==null?null:p.get(key).toString();
		}
	}
	
	public static Map<String,String> getPropertys(){
		Map<String,String> map = new HashMap<String,String>();
		for(Object key:p.keySet()) map.put((String)key, getProperty((String)key));
		return map;
	}
}
