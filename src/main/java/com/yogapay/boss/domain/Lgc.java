package com.yogapay.boss.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Lgc {
	private Integer id;
	private String lgcNo;
	private String name;
	private String pingyin;
	private String contact;
	private String website;
	private String logo;
	private Integer hotOrderNo;
	private Integer priceOrderNo;
	private String accessTime;
	private String nextSno ;
	private String trackSrc ;
	
	private String cbname ;
	
	private String ubname ;
	
	private String webTitle ;
	
	private String defaultCity ;
	
	private String defaultAcpt ;
	
	private String reportExam ;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLgcNo() {
		return lgcNo;
	}

	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPingyin() {
		return pingyin;
	}

	public void setPingyin(String pingyin) {
		this.pingyin = pingyin;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getHotOrderNo() {
		return hotOrderNo;
	}

	public void setHotOrderNo(Integer hotOrderNo) {
		this.hotOrderNo = hotOrderNo;
	}

	public Integer getPriceOrderNo() {
		return priceOrderNo;
	}

	public void setPriceOrderNo(Integer priceOrderNo) {
		this.priceOrderNo = priceOrderNo;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getNextSno() {
		return nextSno;
	}

	public void setNextSno(String nextSno) {
		this.nextSno = nextSno;
	}

	
	
	public String getTrackSrc() {
		return trackSrc;
	}

	public void setTrackSrc(String trackSrc) {
		this.trackSrc = trackSrc;
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
	
	

	public String getDefaultAcpt() {
		return defaultAcpt;
	}

	public void setDefaultAcpt(String defaultAcpt) {
		this.defaultAcpt = defaultAcpt;
	}
	
	

	public String getReportExam() {
		return reportExam;
	}

	public void setReportExam(String reportExam) {
		this.reportExam = reportExam;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
