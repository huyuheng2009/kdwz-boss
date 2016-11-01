package com.yogapay.boss;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.boss.utils.StringUtils;

public class Main1 {

	public static void main(String[] args) {
          
		Map<String, Object> oMap = new HashMap<String, Object>() ;
		oMap.put("a", "a") ;
		oMap.put("b", null) ;
		oMap.put("c", new Date(133366)) ;
		
		System.out.println(oMap);
		
		System.out.println(JsonUtil.toJson(oMap));

	}

	public static boolean biger(float f1,float f2,boolean eq) {
		if ((f1-f2)>0.001) {
			return true ;
		}
          if (eq) {
        	  if (Math.abs((f1-f2))<0.001) {
        		  return true ;
  			 }
          }
		return false ;
	}
	
	
	public static boolean letter(float f1,float f2,boolean eq) {
		if ((f2-f1)>0.001) {
			return true ;
		}
		  if (eq) {
        	  if (Math.abs((f1-f2))<0.001) {
        		  return true ;
  			 }
          }
		return false ;
	}
	
}
