package com.yogapay.boss.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * http请求
 * 
 * @author Zhu Jian
 * 
 */
public class HttpUtils {
	/**
	 * post请求 ，超时默认10秒, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public String post(String url, Map<String, String> params) throws Exception {
		return this.post(url, params, 60, HTTP.UTF_8,null);
	}

	/**
	 * post请求, 超时默认10秒
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> params, String charset)
			throws Exception {
		return this.post(url, params, 60, charset,null);
	}

	/**
	 * post请求, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> params, int timeout)
			throws Exception {
		return this.post(url, params, timeout, HTTP.UTF_8,null);
	}
	
	/**
	 * post请求, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public String post(String url, Map<String, String> params, Map<String, String> headers)
			throws Exception {
		return this.post(url, params, 60, HTTP.UTF_8,headers);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws IOException
	 */
	public String post(String url, Map<String, String> params, int timeout,
			String charset,Map<String, String> headers) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout",
				timeout * 1000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		String retVal = "";
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					charset);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			if (headers != null) {
				for (Map.Entry<String, String> header : headers.entrySet()) {
					httppost.addHeader(header.getKey(), header.getValue());
				}
			}
			HttpResponse resp = httpclient.execute(httppost);
			retVal = EntityUtils.toString(resp.getEntity(), charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}
	
	/**
	* Post请求byte[] 返回类型
	* @param url
	* @param params
	* @param timeout
	* @param charset
	* @return
	* @throws Exception
	* @return byte[] 返回类型
	 */
	public byte[] postAsByte(String url, Map<String, String> params, int timeout,
			String charset) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout",
				timeout * 1000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		byte[] retVal = null;
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					charset);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			HttpResponse resp = httpclient.execute(httppost);
			retVal = EntityUtils.toByteArray(resp.getEntity());
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * 发送post请求到Google短链接服务器，获取url的短链接
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String postGoogleURLShortener(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout", 10000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		String retVal = url;
		try {
			HttpPost httppost = new HttpPost(
					"https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyABoOMQswr72iix40kj1I42Dk7tBf0kppk");
			httppost.setHeader("Content-Type", "application/json");
			httppost.setEntity(new StringEntity("{\"longUrl\": \"" + url
					+ "\"}"));
			HttpResponse resp = httpclient.execute(httppost);
			String jsonStr = EntityUtils.toString(resp.getEntity(), HTTP.UTF_8);
			if (StringUtils.isNotEmpty(jsonStr)) {
				JSONObject jsonObj = JSONObject.fromObject(jsonStr);
				if (jsonObj.containsKey("id")) {
					retVal = jsonObj.getString("id");
				}
			}
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	
	public  String postJson(String url, String json, int timeout, Object... objects) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setIntParameter("http.socket.timeout", timeout * 1000);
        httpclient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        String retVal = "";
        try {
//            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//            if (params != null) {
//                for (Map.Entry<String, String> param : params.entrySet()) {
//                    formparams.add(new BasicNameValuePair(param.getKey(), param.getValue()));
//                }
//            }
            String encoding = HTTP.UTF_8;
            if(objects != null && objects.length>0){
                encoding = objects[0].toString();
            }
//            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, encoding);
//            HttpPost httppost = new HttpPost(NetUtil.strParseToNet(url));
            HttpPost httppost = new HttpPost(url);
//            httppost.setEntity(entity);
            StringEntity params =new StringEntity(json,encoding);  
            httppost.addHeader("content-type", "application/json");  
            httppost.setEntity(params);  
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            if(objects == null || objects.length ==0){
                retVal = new String(httpclient.execute(
                        httppost, responseHandler).getBytes(HTTP.ISO_8859_1),
                        HTTP.UTF_8);
            }else if(objects !=null && objects[0].equals("utf-8")){
                retVal = httpclient.execute(httppost, responseHandler);
            }else if(objects !=null && objects[0].equals("gb2312")){
                retVal = new String(httpclient.execute(httppost, responseHandler).getBytes("iso-8859-1"),"gb2312");
            }else{
                retVal = new String(httpclient.execute(
                        httppost, responseHandler).getBytes(),
                        HTTP.UTF_8);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return retVal;
    }
	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params, int timeout,
			String charset) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout",
				timeout * 1000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		String retVal = "";
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + "?" + paramstr;
			}
			HttpGet httpget = new HttpGet(url);

			HttpResponse resp = httpclient.execute(httpget);
			retVal = EntityUtils.toString(resp.getEntity(), charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @param charset
	 *            编码方式
	 * @param fNameEndChar
	 *            方法名后结束字符 默认“?”
	 * @return
	 * @throws Exception
	 */
	public static String get(String url, Map<String, String> params,
			int timeout, String charset, String fNameEndChar) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setIntParameter("http.socket.timeout",
				timeout * 1000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		String retVal = "";
		fNameEndChar = (fNameEndChar == null || "".endsWith(fNameEndChar)) ? "?"
				: fNameEndChar;
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + fNameEndChar + paramstr;
			}
			HttpGet httpget = new HttpGet(url);

			HttpResponse resp = httpclient.execute(httpget);
			retVal = EntityUtils.toString(resp.getEntity(), charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求,超时默认10秒
	 * 
	 * @param url
	 * @param params
	 * @param charset
	 *            编码方式
	 * @return
	 * @throws IOException
	 */
	public String get(String url, Map<String, String> params, String charset)
			throws Exception {
		return this.get(url, params, 60, charset);
	}

	/**
	 * get请求,超时默认10秒, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params) throws Exception {
		return this.get(url, params, 60, HTTP.UTF_8);
	}

	/**
	 * get请求, 默认utf-8
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws Exception
	 */
	public String get(String url, Map<String, String> params, int timeout)
			throws Exception {
		return this.get(url, params, timeout, HTTP.UTF_8);
	}
	
	public String getJsonp(String url) throws Exception {
		return this.getJsonp(url, null);
	}
	
	public String getJsonp(String url, Map<String, String> params) throws Exception {
		return this.getJsonp(url, params, 60, HTTP.UTF_8);
	}

	
	public String getJsonp(String url, Map<String, String> params, int timeout)throws Exception {
		return this.getJsonp(url, params, timeout, HTTP.UTF_8);
	}
	
	
	public String getJsonp(String url, Map<String, String> params, int timeout,
			String charset) throws Exception {

		ThreadSafeClientConnManager tm = new ThreadSafeClientConnManager();
		DefaultHttpClient httpclient = new DefaultHttpClient(tm);
		httpclient.addRequestInterceptor(new HttpRequestInterceptor() {
			public void process(final HttpRequest request,
					final HttpContext context) throws HttpException,
					IOException {
				request.removeHeaders("User-Agent");
				request.addHeader("User-Agent","Mozilla/5.0 (Windows NT 5.1; rv:21.0) Gecko/20100101 Firefox/21.0");
				//request.addHeader("Content-Type","text/javascript; charset=UTF-8");
				request.addHeader("Accept","*/*");
				request.addHeader("Accept-Encoding","gzip, deflate");
				request.addHeader("Accept-Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
				request.addHeader("Connection","keep-alive");
				request.addHeader("Host","i56.1688.com");
				request.addHeader("Referer","http://tieba.baidu.com/");
				
				
				/*	 
					
					
				Cookie	JSESSIONID=SVmFyf1-FhyPWrDMUVRVICTuZ7-RVGB7CO-ps5A; _tmp_ck_0="5qZiWZ7kx3yUPYb0LhGwvr4D7tZvTVO6QRNfWPMsYrs9NzyjjP8sbePuoKzq%2FLxhDZP4QXXgEOZNdDQu7W4EXRUsCh3idNz2Hau7iyW4V9bzCnj1HwG1ZNkb2ed%2BtY3s9s0W6LxA9yvib8ZH6IweVuIbQyMtw60PtinAkKGzshnJn6Kk762XhfmkKmzhtMSv02OwbP3lmUw1okmm1M7bxvbjrRB6JguxOAkeY1qxGM55%2Ft4rJr1fqZauUNn5moz2jls9GtKsWao8I7R3KCL%2BixmKPaTsVaPV4B3QkzKOdHCZdcXvSREhhhW2iZveBuoSk6nyukL3VgJKUTzA9Z7vS%2FL20qvOKCC0"; ali_beacon_id=113.106.165.145.1374305367980.402948.4; sync_cookie=true; cna=XGNJCr4XDgACAXFqpZFqAac8
				Host	
				Referer	
				User-Agent	
			
			*/
			
			
			}

			
			
			
			
			
		});
		 
		String retVal = "";
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, charset);
			if (StringUtils.isNotEmpty(paramstr)) {
				url = url + "?" + paramstr;
			}
			HttpGet httpget = new HttpGet(url);

			HttpResponse resp = httpclient.execute(httpget);
			Header [] header=resp.getHeaders("Content-Encoding");
			if(null!=header&&header.length>=1){
				for(Header h : header){
					if(h.toString().indexOf("Content-Encoding: gzip")!=-1){
						GZIPInputStream g=new GZIPInputStream(resp.getEntity().getContent());
						return IOUtils.toString(g);
					}
				}
			}
			retVal = EntityUtils.toString(resp.getEntity(), charset);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;

	}

	
	public static InputStream getSoapInputStream(String requestAddress,
			String requestData) {
		InputStream is = null;
		System.out.println("开始创建连接");
		try {
			URL U = new URL(requestAddress);
			URLConnection conn = U.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
			byte[] bts = requestData.getBytes();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Content-type",	"application/x-www-form-urlencoded");
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setRequestProperty("Content-Length",	Integer.toString(bts.length));
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.connect();
			httpUrlConnection.getOutputStream().write(bts, 0, bts.length);
			httpUrlConnection.getOutputStream().flush();
			httpUrlConnection.getOutputStream().close();
			is = httpUrlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	/**
	 * @param requestAddress
	 * @param requestData
	 * @return
	 */
	public static InputStream getSoapInputStreamByGet(String requestAddress,
			String requestData) {
		InputStream is = null;
		System.out.println("开始创建连接");
		try {
			URL U = new URL(requestAddress);
			URLConnection conn = U.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
			byte[] bts = requestData.getBytes();
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Content-type",	"application/x-www-form-urlencoded");
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Content-Length",	Integer.toString(bts.length));
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.connect();
			httpUrlConnection.getOutputStream().write(bts, 0, bts.length);
			httpUrlConnection.getOutputStream().flush();
			httpUrlConnection.getOutputStream().close();
			is = httpUrlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	
	
	
}