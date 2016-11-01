package com.yogapay.boss.utils.http;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
	
	
	public static String post(String url, Map<String, String> params, int timeout) throws Exception {
		 Map<String, String> requestProperty = new HashMap<String, String>();
		 return	 CommonHttpConnection.proccess(new HttpConnectionParameters(url, "POST",timeout , true, true, true, requestProperty), params);
	}
	
	public static String post(String url, Map<String, String> params) throws Exception {
		 Map<String, String> requestProperty = new HashMap<String, String>();
		 return	 CommonHttpConnection.proccess(new HttpConnectionParameters(url, "POST",22000 , true, true, true, requestProperty), params);
	}
	
	public static String get(String url, Map<String, String> params, int timeout)throws Exception {
		 Map<String, String> requestProperty = new HashMap<String, String>();
		 return	 CommonHttpConnection.proccess(new HttpConnectionParameters(url, "GET",timeout , true, true, true, requestProperty), params);
	}
	
	public static String get(String url, Map<String, String> params) throws Exception {
		 Map<String, String> requestProperty = new HashMap<String, String>();
		 return	 CommonHttpConnection.proccess(new HttpConnectionParameters(url, "GET",22000 , true, true, true, requestProperty), params);
	}
	
	
	
	public static void main(String [] args) throws Exception {
	/*	 Map<String, String> params = new HashMap<String, String>() ;
		 params.put("type", "yuantong") ;
		 params.put("postid", "560031072312") ;
		 System.out.println(HttpUtil.get("http://www.kuaidi100.com/query", params)); 
		
		 System.out.println("******************************");
		 HttpUtils httpUtils = new HttpUtils() ; 
		 System.out.println(httpUtils.get("http://www.kuaidi100.com/query", params));*/
		
		//KuaiDiYiBaiUtil.track(null, "") ;
		
		
		 Map<String, String> params = new HashMap<String, String>() ;
		 params.put("reqTime", "yuantong") ;
		 params.put("reqNo", "560031072312") ;
		 params.put("appVersion", "12") ;

		 Map<String, String> headers = new HashMap<String, String>() ;
		 headers.put("sessionNO", "097db4b9233d4db78516b2c01c6bf2b6") ;
		 
		 HttpUtils httpUtils = new HttpUtils() ; 
		 System.out.println(httpUtils.post("http://192.168.1.30:6033/card/list", params,headers));
		 
		
		
	}

}
