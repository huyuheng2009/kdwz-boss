package com.yogapay.boss.domain;


public class TableFiledSort {
	private long id;
	private String tab;
	private String tabName;
	private String col;
	private String colName;
	private int sort;
	private int isShow;
	private String userNo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getIsShow() {
		return isShow;
	}
	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "TableFiledSort [id=" + id + ", tab=" + tab + ", tabName=" + tabName + ", col=" + col + ", colName="
				+ colName + ", sort=" + sort + ", isShow=" + isShow + ", userNo=" + userNo + "]";
	}
	
	
}
