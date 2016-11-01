/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import java.text.DecimalFormat;

/**
 *
 * @author zeng
 */
public class MapDistance {
//
//	private static double EARTH_RADIUS = 6378.137;
//
//	private static double rad(double d) {
//		return d * Math.PI / 180.0;
//	}
//
//	/**
//	 * 根据两个位置的经纬度，来计算两地的距离（单位为KM)
//	 *
//	 * @param lat1Str 用户经度
//	 * @param lng1Str 用户纬度
//	 * @param lat2Str
//	 * @param lng2Str
//	 * @return
//	 */
//	public static double getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
//		Double lat1 = Double.parseDouble(lat1Str);
//		Double lng1 = Double.parseDouble(lng1Str);
//		Double lat2 = Double.parseDouble(lat2Str);
//		Double lng2 = Double.parseDouble(lng2Str);
//		double radLat1 = rad(lat1);
//		double radLat2 = rad(lat2);
//		double difference = radLat1 - radLat2;
//		double mdifference = rad(lng1) - rad(lng2);
//		double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)+ Math.cos(radLat1) * Math.cos(radLat2)
//				* Math.pow(Math.sin(mdifference / 2), 2)));
//		distance = distance * EARTH_RADIUS;
//		distance = Math.round(distance * 10000) / 10000;
//		DecimalFormat df = new DecimalFormat("#.00");
//		String distanceStr = df.format(distance);
//		return Double.parseDouble(distanceStr);
//	}
//	public static double getShortestDistance(double lat1, double lng1, double lat2,  
//            double lng2){
//		 double radLat1 = rad(lat1);  
//	        double radLat2 = rad(lat2);  
//	        double a = radLat1 - radLat2;  
//	        double b = rad(lng1) - rad(lng2);  
//	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
//	                + Math.cos(radLat1) * Math.cos(radLat2)  
//	                * Math.pow(Math.sin(b / 2), 2)));  
//	        s = s * EARTH_RADIUS;  
//	        s = Math.round(s * 10000) / 10000;  
//	        return s;  
//
//		}
	public static void main(String[] args) {
//		System.out.println(getDistance(22.580372, 113.953140, 22.531221, 113.929428));
		System.out.println(getDistance("22.580372", "113.953140", "22.776651", "113.891388"));
	}
	  private final static double PI = 3.14159265358979323; // 圆周率
	    private final static double R = 6378137; // 地球的半径

	    public static double getDistance(String longt1, String lat11, String longt2,String lat22) {
	    	if(StringUtil.isEmpty(longt1) ||StringUtil.isEmpty(lat11) ||StringUtil.isEmpty(longt2) ||StringUtil.isEmpty(lat22) ){
	    		return 0.0;
	    	}
	        double x, y, distance;
			Double lat1 = Double.parseDouble(lat11);
			Double lng1 = Double.parseDouble(longt1);
			Double lat2 = Double.parseDouble(lat22);
			Double lng2 = Double.parseDouble(longt2);
	        x = (lng2 - lng1) * PI * R * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
	        y = (lat2 - lat1) * PI * R / 180;
	        distance = Math.hypot(x, y);
	        distance=Math.round(distance);
	        return distance;
	    }
}
