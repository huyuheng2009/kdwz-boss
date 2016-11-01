package com.yogapay.boss.domain;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{
	
	private static final long serialVersionUID = 201607201L;
	
	private int id ;
	
	private String menuUri ;
	
	private String menuText ;
	
	private String menuImg;
	
	private String permisionCode;
	
	private int pid ;
	
	private int level ;
	
	private int orderNo ;
	
	private List<Menu> nodeList ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	public String getMenuText() {
		return menuText;
	}

	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}

	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	public String getPermisionCode() {
		return permisionCode;
	}

	public void setPermisionCode(String permisionCode) {
		this.permisionCode = permisionCode;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}


	public List<Menu> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<Menu> nodeList) {
		this.nodeList = nodeList;
	}
	
	

}
