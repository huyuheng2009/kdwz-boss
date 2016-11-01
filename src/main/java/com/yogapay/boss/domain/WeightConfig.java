package com.yogapay.boss.domain;

public class WeightConfig {
	private long id;
	private double revMinv;
	private String createTime;
	private double warehouseMinv;
	private int selectv;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getRevMinv() {
		return revMinv;
	}
	public void setRevMinv(double revMinv) {
		this.revMinv = revMinv;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public double getWarehouseMinv() {
		return warehouseMinv;
	}
	public void setWarehouseMinv(double warehouseMinv) {
		this.warehouseMinv = warehouseMinv;
	}
	public int getSelectv() {
		return selectv;
	}
	public void setSelectv(int selectv) {
		this.selectv = selectv;
	}
	
	
}