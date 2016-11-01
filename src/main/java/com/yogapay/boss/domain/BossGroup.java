package com.yogapay.boss.domain;

import java.util.Date;

public class BossGroup {
	
	private int id ;
	
	private String groupName ;
	
	private String groupDesc ;
	
	private String createTime ;
	
	private String clogin ;
	
	private String creator ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClogin() {
		return clogin;
	}

	public void setClogin(String clogin) {
		this.clogin = clogin;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	
}
