/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.domain;

/**
 *
 * @author zeng
 */
public class OrderAddr {

	private int id;
	private String sendOrderTime;
	private String monthNo;
	private String takeCourierNo;
	private String sendCourierNo;
	private String revName;
	private String revArea;
	private String revAddr;
	private String revPhone;
	private String sendName;
	private String sendArea;
	private String sendAddr;
	private String sendPhone;
	private String source;
	private String itemName;//物品名称
	private String itemStatus;//物品类别
	private String freightType;//邮费类型
	private String payType;//支付方式
	private String goodValuation;//物品报价额
	private String goodPrice;
	private String userName;
	private String vpay;
	private String itemWeight;
	private String lgcOrderNo;
	private String freight;
	private String orderNote;//备注
	private String lgcNo;
	
	public String getLgcNo() {
		return lgcNo;
	}

	public void setLgcNo(String lgcNo) {
		this.lgcNo = lgcNo;
	}

	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}

	public String getMonthNo() {
		return monthNo;
	}

	public void setMonthNo(String monthNo) {
		this.monthNo = monthNo;
	}

	public String getSendOrderTime() {
		return sendOrderTime;
	}

	public void setSendOrderTime(String sendOrderTime) {
		this.sendOrderTime = sendOrderTime;
	}

	public String getTakeCourierNo() {
		return takeCourierNo;
	}

	public void setTakeCourierNo(String takeCourierNo) {
		this.takeCourierNo = takeCourierNo;
	}

	public String getSendCourierNo() {
		return sendCourierNo;
	}

	public void setSendCourierNo(String sendCourierNo) {
		this.sendCourierNo = sendCourierNo;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getSendArea() {
		return sendArea;
	}

	public void setSendArea(String sendArea) {
		this.sendArea = sendArea;
	}

	public String getSendAddr() {
		return sendAddr;
	}

	public void setSendAddr(String sendAddr) {
		this.sendAddr = sendAddr;
	}

	public String getSendPhone() {
		return sendPhone;
	}

	public void setSendPhone(String sendPhone) {
		this.sendPhone = sendPhone;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRevName() {
		return revName;
	}

	public void setRevName(String revName) {
		this.revName = revName;
	}

	public String getRevArea() {
		return revArea;
	}

	public void setRevArea(String revArea) {
		this.revArea = revArea;
	}

	public String getRevAddr() {
		return revAddr;
	}

	public void setRevAddr(String revAddr) {
		this.revAddr = revAddr;
	}

	public String getRevPhone() {
		return revPhone;
	}

	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public String getFreightType() {
		return freightType;
	}

	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getGoodValuation() {
		return goodValuation;
	}

	public void setGoodValuation(String goodValuation) {
		this.goodValuation = goodValuation;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVpay() {
		return vpay;
	}

	public void setVpay(String vpay) {
		this.vpay = vpay;
	}

	

	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}

	

	public String getLgcOrderNo() {
		return lgcOrderNo;
	}

	public void setLgcOrderNo(String lgcOrderNo) {
		this.lgcOrderNo = lgcOrderNo;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}
}
