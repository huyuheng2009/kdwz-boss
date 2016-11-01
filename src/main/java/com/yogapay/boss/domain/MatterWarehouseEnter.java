package com.yogapay.boss.domain;

import java.util.Date;

/**
 * 物料入库记录
 * @author 005
 *
 */

public class MatterWarehouseEnter {
	
	private int id ; 
	
	private String substationNo ;
	
	private int matterId ; 
	
	private String bplaneNo ;
	
	private String eplaneNo;
	
	private Float matterPrice;
	
	private Float mcount;
	
	private Float macount;
	
	private String brokerage;
	
	private String supplier;
	
	private String wareTime;
	
	private String note;
	
	private String operator;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubstationNo() {
		return substationNo;
	}

	public void setSubstationNo(String substationNo) {
		this.substationNo = substationNo;
	}

	public int getMatterId() {
		return matterId;
	}

	public void setMatterId(int matterId) {
		this.matterId = matterId;
	}

	public String getBplaneNo() {
		return bplaneNo;
	}

	public void setBplaneNo(String bplaneNo) {
		this.bplaneNo = bplaneNo;
	}

	public String getEplaneNo() {
		return eplaneNo;
	}

	public void setEplaneNo(String eplaneNo) {
		this.eplaneNo = eplaneNo;
	}

	public Float getMatterPrice() {
		return matterPrice;
	}

	public void setMatterPrice(Float matterPrice) {
		this.matterPrice = matterPrice;
	}

	public Float getMcount() {
		return mcount;
	}

	public void setMcount(Float mcount) {
		this.mcount = mcount;
	}

	public Float getMacount() {
		return macount;
	}

	public void setMacount(Float macount) {
		this.macount = macount;
	}

	public String getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getWareTime() {
		return wareTime;
	}

	public void setWareTime(String wareTime) {
		this.wareTime = wareTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
	

}
