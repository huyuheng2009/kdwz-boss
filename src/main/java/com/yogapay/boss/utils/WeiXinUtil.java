package com.yogapay.boss.utils;

import java.util.Map;
import com.yogapay.boss.utils.http.HttpUtils;




public class WeiXinUtil {
	 
	 public static final String WEIXINPUSH = ConstantsLoader.getProperty("wxpush_host")+"";// 
	 
	
	public static void push(Map<String, String> params) throws Exception {
		WXPushThread pThread = new WXPushThread(params) ;
		Thread t = new Thread(pThread) ;
		t.start();
	}
}

  class WXPushThread implements Runnable{
	Map<String, String> params ;
	public WXPushThread(Map<String, String> params) {
    this.params = params ;
	}

	@Override
	public void run() {
		 HttpUtils httpUtils = new HttpUtils() ; 
		 Map<String, String> header = null ;
		 try {
			 System.out.println("WXPushThread start-------------------");
			 System.out.println(WeiXinUtil.WEIXINPUSH);
		   String r = httpUtils.post(WeiXinUtil.WEIXINPUSH, params, header) ;
           System.out.println(r);
           System.out.println("WXPushThread end---------------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


  
