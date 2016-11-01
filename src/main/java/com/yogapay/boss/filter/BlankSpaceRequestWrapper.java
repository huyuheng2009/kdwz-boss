package com.yogapay.boss.filter;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

public class BlankSpaceRequestWrapper extends HttpServletRequestWrapper {
	public BlankSpaceRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 根据指定的参数名，返回去除前后空白字符的参数值。
	 * 
	 * @param name
	 *            参数名
	 * @return 去除前后空格的参数值
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		return StringUtils.trim(super.getParameter(name));
	}

	/**
	 * 返回参数名-参数值数组映射，其中的参数数组将被去除前后空白字符。
	 * 
	 * @return 去除空白字符的参数名-参数值数组映射
	 * @see javax.servlet.ServletRequestWrapper#getParameterMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getParameterMap() {
		Map<String, String[]> parameterMap = new LinkedHashMap<String, String[]>();
		Enumeration<String> names = super.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			parameterMap.put(name, getParameterValues(name));
		}
		return parameterMap;
	}

	/**
	 * 根据指定的参数名，返回去除前后空格的参数值数组。
	 * 
	 * @param name
	 *            参数名
	 * @return 去除前后空格的参数值数组
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		if (null != super.getParameterValues(name)) {
			String[] values = super.getParameterValues(name).clone();
			for (int i = 0; i < values.length; i++) {
				values[i] = StringUtils.trim(values[i]);
			}
			return values;
		}
		return null;
	}
}