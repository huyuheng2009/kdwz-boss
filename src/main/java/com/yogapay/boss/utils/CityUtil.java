/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.utils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yogapay.boss.domain.City;

/**
 *
 * @author zeng
 */
public class CityUtil {

	public static String getCityArea(List<City> list, String clickTag, String areaTag, String cid, String cityFlag, String areaFlag, String selectFlag) {
		TreeMap<String, City> map1 = new TreeMap<String, City>();
		TreeMap<String, City> map2 = new TreeMap<String, City>();
		TreeMap<String, City> map3 = new TreeMap<String, City>();
		TreeMap<String, City> map4 = new TreeMap<String, City>();
		for (City city : list) {
			String t = PingYinUtil.getPinYinHeadSingleChar(city.getName());
			if (PingYinUtil.letterToNum(t) <= 71) {
				map1.put(city.getName(), city);
			} else if (PingYinUtil.letterToNum(t) > 71 && PingYinUtil.letterToNum(t) <= 77) {
				map2.put(city.getName(), city);
			} else if (PingYinUtil.letterToNum(t) > 77 && PingYinUtil.letterToNum(t) <= 83) {
				map3.put(city.getName(), city);
			} else {
				map4.put(city.getName(), city);
			}
		}
		StringBuilder buffer = new StringBuilder();
		if (map1.size() > 0) {
			buffer.append("<div class=\"addr_cli\"><strong>A-G</strong><div class=\"addr_point\">");
			for (Map.Entry<String, City> entry : map1.entrySet()) {
				City c = entry.getValue();
				buffer.append("<button type=\"button\" onclick=\"" + clickTag + "('" + c.getId() + "','" + c.getName() + "','" + areaTag + "','" + cid + "','" + cityFlag + "','" + areaFlag + "','" + selectFlag + "')\">").append(c.getName()).append("</button>");
			}
			buffer.append("</div></div>");
		}
		if (map2.size() > 0) {
			buffer.append("<div class=\"addr_cli\"><strong>H-M</strong><div class=\"addr_point\">");
			for (Map.Entry<String, City> entry : map2.entrySet()) {
				City c = entry.getValue();
				buffer.append("<button type=\"button\" onclick=\"" + clickTag + "('" + c.getId() + "','" + c.getName() + "','" + areaTag + "','" + cid + "','" + cityFlag + "','" + areaFlag + "','" + selectFlag + "')\">").append(c.getName()).append("</button>");
			}
			buffer.append("</div></div>");
		}
		if (map3.size() > 0) {
			buffer.append("<div class=\"addr_cli\"><strong>N-S</strong><div class=\"addr_point\">");
			for (Map.Entry<String, City> entry : map3.entrySet()) {
				City c = entry.getValue();
				buffer.append("<button type=\"button\" onclick=\"" + clickTag + "('" + c.getId() + "','" + c.getName() + "','" + areaTag + "','" + cid + "','" + cityFlag + "','" + areaFlag + "','" + selectFlag + "')\">").append(c.getName()).append("</button>");
			}
			buffer.append("</div></div>");
		}
		if (map4.size() > 0) {
			buffer.append("<div class=\"addr_cli\"><strong>T-Z</strong><div class=\"addr_point\">");
			for (Map.Entry<String, City> entry : map4.entrySet()) {
				City c = entry.getValue();
				buffer.append("<button type=\"button\" onclick=\"" + clickTag + "('" + c.getId() + "','" + c.getName() + "','" + areaTag + "','" + cid + "','" + cityFlag + "','" + areaFlag + "','" + selectFlag + "')\">").append(c.getName()).append("</button>");
			}
			buffer.append("</div></div>");
		}
		return buffer.toString();
	}
}
