package com.yogapay.boss;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yogapay.boss.utils.JsonUtil;
import com.yogapay.util.HttpUtils;

public class Test11 {

	public static void main(String[] args) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("url", "11");
		map.put("touser", "oqrcIwgUl6iPe90icx8O9St641y0"); //   ob0EbuMJwZNupiTDTZR7UyxHXxYY  //o6MOIt8wZbVJGDN29C5fQuUAth5E
		map.put("template_id","Yqutd1iTb_O4DkSGXAvKrPP6X6Hh2qCnWLzb0eFZAHo");
		
		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>() ;
		 Map<String, String> lgcOrderNo = new HashMap<String, String>() ;
		 lgcOrderNo.put("value", "lgcOrderNo");
		 
		 Map<String, String> company = new HashMap<String, String>() ;
		 company.put("value", "我们");
		 
		 Map<String, String> remark = new HashMap<String, String>() ;
		 remark.put("value", "remark");
		 
		 data.put("orderNumber", lgcOrderNo) ;
		 data.put("company", company) ;
		 data.put("remark", remark) ;
		 map.put("data", data) ;
		 
		 String post =  JsonUtil.toJson(map);
		 System.out.println(post);
		 
		HttpUtils httpUtils = new HttpUtils() ;
		String token = "dOwPcysov-kzQu9_2YOWa3i1MAC1qSF4qMStF10eMgy__eeZcNyUk5KvGvxKB8vNC2soV7if1gw5XJN7-f9kJhDuHLp1qoK3MRln808B66DVMZLnNyU4v3jLILqoanv9OHIaADARKL" ;
		String result = httpUtils.postJson("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token, post, 60) ;
		//String result = httpUtils.postJson("https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+token, post, 60) ;
	    System.out.println(result);
	}

}
