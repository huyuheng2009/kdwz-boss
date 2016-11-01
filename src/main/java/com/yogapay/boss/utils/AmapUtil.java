/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.yogapay.boss.utils.http.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author zeng
 */
public class AmapUtil {

    private static String APIURL = "http://restapi.amap.com/v3/geocode/geo?key=9254426ed0d9ba8e6e1fbadab66fda46&s=rsv3&address=";

    public static Map<String, String> addressToGPS(String address) throws Exception {

	String requestUrl = APIURL + URLEncoder.encode(address, "UTF-8");
	String resultJson = readContentFromGet(requestUrl);
	Map<String, String> param = new HashMap<String, String>();
	JSONObject object = JSONObject.fromObject(resultJson);
	JSONArray geocodes = object.getJSONArray("geocodes");
	if (geocodes.size() == 0) {
		param.put("lat", "0");
	    param.put("lng", "0");
	} else if (geocodes.size() == 1) {
	    JSONObject trueAddress = geocodes.getJSONObject(0);
	    System.out.println("trueAddress====================="+trueAddress);
	    String location = trueAddress.getString("location");
	    System.out.println("location====================="+location);
	    String lngX = location.split(",")[0];
	    String latY = location.split(",")[1];
	    param.put("lat", latY);
	    param.put("lng", lngX);
	} else {
		 param.put("lat", "0");
		 param.put("lng", "0");
	}
	return param;
    }

    public static String addressToGPString(String address) throws Exception {

        Map<String, String> params = new HashMap<String, String>() ;
        params.put("key", "9254426ed0d9ba8e6e1fbadab66fda46") ;
        params.put("address", "广东省深圳市福田区天安数码城创业科技大厦一期") ;
        HttpUtils httpUtils = new HttpUtils() ; 
        String resultJson =  httpUtils.post("http://restapi.amap.com/v3/geocode/geo", params) ;
    	String ret = "" ;
    	JSONObject object = JSONObject.fromObject(resultJson);
    	JSONArray geocodes = object.getJSONArray("geocodes");
    	if (geocodes.size() == 0) {
    		 ret = "" ;
    	} else if (geocodes.size() == 1) {
    	    JSONObject trueAddress = geocodes.getJSONObject(0);
    	    String location = trueAddress.getString("location");
    	   ret = location ;
    	  }
    	return ret;
        }
    
    public static void main(String[] args) throws IOException, Exception {
	System.out.println(AmapUtil.addressToGPString("广东省深圳市福田区天安数码城创业科技大厦一期"));
//	String addressStr = "http://restapi.amap.com/v3/geocode/geo?key=e06d4341a635e738a7c856bb38b37812&s=rsv3&address=";
//	String getAddress = "";
//	URL getUrl = new URL(addressStr + URLEncoder.encode("塘朗村口", "UTF-8"));
//	// 根据拼凑的URL，打开连接，URL.openConnection函数会根据 URL的类型，
//	// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
//	HttpURLConnection connection = (HttpURLConnection) getUrl
//		.openConnection();
//	// 进行连接，但是实际上get request要在下一句的 connection.getInputStream()函数中才会真正发到
//	// 测试
//	connection.setDoInput(true);
//	connection.setDoOutput(true);
//	connection.setRequestMethod("GET");
//	connection
//		.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
//	connection.setRequestProperty("User-Agent",
//		"Jakarta Commons-HttpClient/3.1");
//	connection.setConnectTimeout(50000);
//	connection.setReadTimeout(50000);
//	// 测试
//
//	// 服务器
//	connection.connect();
//	// 取得输入流，并使用Reader读取
//	BufferedReader reader = new BufferedReader(new InputStreamReader(
//		connection.getInputStream(), "UTF-8"));
//	String lines;
//	while ((lines = reader.readLine()) != null) {
//	    getAddress += lines;
//	}
//	reader.close();
//	// 断开连接
//	connection.disconnect();
//	System.out.print(getAddress);

    }
    public static String readContentFromGet(String getURL) throws IOException {
        String returnStr = "";// 返回的字符串
        // try {
        URL getUrl = new URL(getURL);
        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据 URL的类型，
        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        // 进行连接，但是实际上get request要在下一句的 connection.getInputStream()函数中才会真正发到
        // 测试
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection
                .setRequestProperty("Content-Type", "text/html;charset=UTF-8");
        connection.setRequestProperty("User-Agent",
                "Jakarta Commons-HttpClient/3.1");
        connection.setConnectTimeout(50000);
        connection.setReadTimeout(50000);
        // 测试

        // 服务器
        connection.connect();
        // 取得输入流，并使用Reader读取
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "UTF-8"));
        String lines;
        while ((lines = reader.readLine()) != null) {
            returnStr += lines;
        }
        reader.close();
        // 断开连接
        connection.disconnect();
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // }
        return returnStr;
    }

}
