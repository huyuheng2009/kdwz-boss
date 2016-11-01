package com.yogapay.boss.service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.yogapay.boss.utils.http.HttpUtil;
import com.yogapay.boss.utils.http.HttpUtils;

public class Test
{
  public static void main(String[] args) throws Exception {
    sendRequest();
  }
  
  private static void sendRequest() throws Exception {
    
    // 查询物流价格 (POST )
    
    try {
      
      
 /*     Map<String, String> params = new HashMap<String, String>() ;
      
      params.put("weigth", "0.5") ;
      params.put("street", "") ;
      params.put("deliveryType", "TO_DOOR") ;
      params.put("endPlace", "411327") ;
      params.put("startPlace_input", "广东深圳南山") ;
      params.put("delivery", "YES") ;
      params.put("startPlace", "440305") ;
      params.put("vistReceive", "YES") ;
      params.put("endPlace_input", "河南-南阳-社旗") ;
      
      HttpUtils httpUtils = new HttpUtils() ; 
      
      String content =  httpUtils.post("http://www.kuaidi100.com/order/unlogin/price.do?action=searchOrderPrice&currentPage=1&pageSize=100", params) ;
      */
      
      Map<String, String> params = new HashMap<String, String>() ;
      params.put("key", "9254426ed0d9ba8e6e1fbadab66fda46") ;
      params.put("address", "广东省深圳市福田区天安数码城创业科技大厦一期") ;
      HttpUtils httpUtils = new HttpUtils() ; 
      String content =  httpUtils.post("http://restapi.amap.com/v3/geocode/geo", params) ;
      // Print content
      System.out.println(content);
    }
    catch (IOException e) { System.out.println(e); }
  }
}
