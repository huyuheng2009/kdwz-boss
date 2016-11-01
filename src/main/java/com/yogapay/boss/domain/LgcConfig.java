package com.yogapay.boss.domain;

import java.io.Serializable;
import java.util.Map;

public class LgcConfig implements Serializable{
	
	private static final long serialVersionUID = 20160720L;

	private String key ;
	
	private String host ;

	private String lgcNo ;
	
	private String cbname ;
	
	private String ubname ;
	
	private String webTitle ;
	
	private String defaultCity ;
	
	private String webUrl ;
	
	private String curName ;  //当前用户名称
	
	private String reportExam  ;
	
	
	private Map<String, String> res ;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getLgcNo() {
		return lgcNo;
	}

	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}

	public String getCbname() {
		return cbname;
	}

	public void setCbname(String cbname) {
		this.cbname = cbname;
	}

	public String getUbname() {
		return ubname;
	}

	public void setUbname(String ubname) {
		this.ubname = ubname;
	}

	public String getWebTitle() {
		return webTitle;
	}

	public void setWebTitle(String webTitle) {
		this.webTitle = webTitle;
	}

	public String getDefaultCity() {
		return defaultCity;
	}

	public void setDefaultCity(String defaultCity) {
		this.defaultCity = defaultCity;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	
	
	public String getReportExam() {
		return reportExam;
	}

	public void setReportExam(String reportExam) {
		this.reportExam = reportExam;
	}

	public Map<String, String> getRes() {
		return res;
	}

	public void setRes(Map<String, String> res) {
		this.res = res;
	}
	
	
}
