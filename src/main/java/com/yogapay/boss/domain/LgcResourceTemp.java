package com.yogapay.boss.domain;

import java.util.HashMap;
import java.util.Map;

public class LgcResourceTemp {
     
	public static Map<String,TempFile> rList ;
	
	public static void add(TempFile file,String rtype) {
		if (rList==null) {
			rList = new HashMap<String, TempFile>() ;
		}
		if (rList.containsKey(rtype)) {
			rList.remove(rtype) ;
		}
		rList.put(rtype, file); 
	}
	
	public static void clear() {
		if (rList!=null) {
			rList.clear();
			rList=null ;
		}
	}
	
	public static Map<String,TempFile> get() {
		if (rList!=null) {
			return new HashMap<String, TempFile>(rList) ;
		}else {
			return new HashMap<String, TempFile>() ; 
		}
	}
}
