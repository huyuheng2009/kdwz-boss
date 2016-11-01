/**
 * 项目: qpos-commons
 * 包名: com.zfj.qpos.commons.utils
 * 文件名: CommonHttpConnection.java
 * 创建时间: 2014-4-1 上午11:20:32
 * 2014 支付界科技有限公司版权所有,保留所有权利;
 */
package com.yogapay.boss.utils.http;

import com.yogapay.boss.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @Todo: Http 链接工具
 * @Author: zhanggc
 * @Date: 2014-4-1
 */
public class CommonHttpConnection {
	/**
	 * @Todo: 获取Http链接
	 * @param params
	 * @return
	 * @throws java.io.IOException
	 */
	public static HttpURLConnection getConnection(HttpConnectionParameters params)
			throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(params.getUrl())
				.openConnection();
		connection.setReadTimeout(params.getTimeout()); // 缓存的最长时间
		connection.setDoInput(params.isInput());// 允许输入
		connection.setDoOutput(params.isOutput());// 允许输出
		connection.setUseCaches(params.isCache()); // 不允许使用缓存
		connection.setRequestMethod(params.getRequestMethod());

		for(String key:params.getRequestProperty().keySet()) connection.setRequestProperty(key,params.getRequestProperty().get(key));

		return connection;
	}

	/**
	 * @Todo: 设置发送参数
	 * @param connection
	 * @param values
	 * @throws java.io.IOException
	 */
	protected static void setParam(HttpURLConnection connection,Map<String,String> values) throws IOException{
		if(values==null) return;
		StringBuilder params = new StringBuilder();
		for(String key:values.keySet()){
			params.append(key)
			.append("=")
			.append(values.get(key))
			.append("&");
		}
		setParam(connection,params.toString());
	}

	/**
	 * @Todo: 设置发送文本
	 * @param connection
	 * @param text
	 * @throws java.io.IOException
	 */
	protected static void setParam(HttpURLConnection connection,String text) throws IOException{
		if(StringUtils.isEmptyWithTrim(text)) return;
		connection.getOutputStream().write(text.getBytes("utf-8"));
		connection.getOutputStream().flush();
		connection.getOutputStream().close();
	}

	// 发送
	protected static String post(HttpURLConnection connection) throws IOException{
		InputStream in = connection.getInputStream();
		int res = connection.getResponseCode();
		StringBuilder sb = new StringBuilder();
		if (res == 200) {
			int ch;
			while ((ch = in.read()) != -1) {
				sb.append((char) ch);
			}
		}
		return sb.toString();
	}

	/**
	 * @Todo: 流程处理
	 * @param params
	 * @param values
	 * @return
	 * @throws java.io.IOException
	 */
	public static String proccess(HttpConnectionParameters params,Map<String,String> values) throws IOException{
		HttpURLConnection connection = getConnection(params);
		setParam(connection,values);
		return post(connection);
	}

	/**
	 * @Todo: 流程处理
	 * @param params
	 * @param text
	 * @return
	 * @throws java.io.IOException
	 */
	public static String proccess(HttpConnectionParameters params,String text) throws IOException{
		HttpURLConnection connection = getConnection(params);
		setParam(connection,text);
		return post(connection);
	}
}
