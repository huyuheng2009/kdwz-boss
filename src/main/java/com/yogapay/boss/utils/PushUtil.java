package com.yogapay.boss.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yogapay.boss.domain.PushMsg;
import com.yogapay.boss.utils.http.HttpUtils;




public class PushUtil {
	 
	 public static final String USERPUSHBYID = ConstantsLoader.getProperty("push_host")+"/push/user/msg";// 
	 
	 public static final String COURIERPUSHBYID = ConstantsLoader.getProperty("push_host")+"/push/courier/msg";// 
	 
	 public static final String COURIERPUSHBYMSG = ConstantsLoader.getProperty("push_host")+"/push/courier/msgdata";// 
 
	
	public static void pushById(String id,int userType,String uid) throws Exception {
		PushThread pThread = new PushThread(id,userType,uid) ;
		Thread t = new Thread(pThread) ;
		t.start();
	}
	
	
	public static void pushByMSG(PushMsg msg,String uid) throws Exception {
		PushMsgThread pThread = new PushMsgThread(msg,uid) ;
		Thread t = new Thread(pThread) ;
		t.start();
	}
	
	public static void pushByBatchMSG(List<PushMsg> msg,String uid){
		PushBatchMsgThread pThread = new PushBatchMsgThread(msg,uid);
		Thread t = new Thread(pThread) ;
		t.start();
	}
}


  class PushThread implements Runnable{
	String msgId ;
	int userType ;
	String uid ;
	public PushThread(String msgId,int userType,String uid ) {
    this.msgId = msgId ;
    this.userType = userType ;
    this.uid = uid ;
	}

	@Override
	public void run() {
		 HttpUtils httpUtils = new HttpUtils() ; 
		 Map<String, String> params = new HashMap<String, String>() ;
		 params.put("msgId", msgId) ;
		 params.put("uid", uid) ;
		 Map<String, String> header = null ;
		 String ret = "" ;
		 try {
			 if (userType==1) {
				 ret = httpUtils.post(PushUtil.USERPUSHBYID, params,header);
			}else {
				ret = httpUtils.post(PushUtil.COURIERPUSHBYID, params,header);
			}
			System.out.println(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
  
  class PushMsgThread implements Runnable{
	  PushMsg msg  ;
	  String uid ;
	public PushMsgThread(PushMsg msg,String uid) {
    this.msg = msg ;
    this.uid = uid ;
	}

	@Override
	public void run() {
		 HttpUtils httpUtils = new HttpUtils() ; 
		 Map<String, String> params = new HashMap<String, String>() ;
		 params.put("msg", JsonUtil.toJson(msg)) ;
		 params.put("uid", uid) ;
		 Map<String, String> header = null ;
		 String ret = "" ;
		 try {
			 ret = httpUtils.post(PushUtil.COURIERPUSHBYMSG, params,header);
			 System.out.println(ret);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
  
  
  class PushBatchMsgThread implements Runnable{
	  List<PushMsg> msg  ;
	  String uid ;
	public PushBatchMsgThread(List<PushMsg> msg,String uid) {
    this.msg = msg ;
    this.uid = uid ;
	}

	@Override
	public void run() {
		 HttpUtils httpUtils = new HttpUtils() ; 
		 Map<String, String> params = new HashMap<String, String>() ;
		 for (PushMsg pushMsg : msg) {
			 params.put("msgId", pushMsg.getId()+"") ;
			 params.put("uid", uid) ;
			 Map<String, String> header = null ;
			 String ret = "" ;
			 try {
				// ret = httpUtils.post(PushUtil.COURIERPUSHBYMSG, params,header);
				 ret = httpUtils.post(PushUtil.COURIERPUSHBYID, params,header);
				 System.out.println(ret);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
  
  
