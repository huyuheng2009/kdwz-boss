package com.yogapay.boss.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yogapay.boss.utils.http.HttpUtils;

public class KuaiDiYiBaiUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> track(String lgc,String orderNo)  {
		Map<String, Object> trackMap = new HashMap<String, Object>() ;
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>() ;
		try {
		 Map<String, String> params = new HashMap<String, String>() ;
		 params.put("type", lgc) ;
		 params.put("postid", orderNo) ;
		 HttpUtils httpUtils = new HttpUtils() ; 
		 String jsonString = httpUtils.get("http://www.kuaidi100.com/query", params) ;
		Map<String, Object> ret = JsonUtil.getMapFromJson(jsonString);
		if (ret==null||!"200".equals(ret.get("status").toString())) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("time", "") ;	
			map.put("location", "") ;
			map.put("context", "无此物流单号信息！") ;
			map.put("ftime", "") ;
			dataList.add(map);
			trackMap.put("ischeck","0") ;
		}else {
			dataList.addAll((Collection<? extends Map<String, String>>) ret.get("data")) ;
			trackMap.put("ischeck",ret.get("ischeck")) ;
		}
		trackMap.put("data",dataList) ;
		} catch (Exception e) {
			Map<String, String> map = new HashMap<String, String>() ;
			map.put("time", "") ;	
			map.put("location", "") ;
			map.put("context", "无此物流单号信息！") ;
			map.put("ftime", "") ;
			dataList.add(map);
			trackMap.put("ischeck","0") ;
		}
		return trackMap;
	}

}
