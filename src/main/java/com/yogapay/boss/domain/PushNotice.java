package com.yogapay.boss.domain;

import java.util.Date;


public class PushNotice {
	private long id;
	
	private String createTime;
	
	private String lastUpdateTime;
	
	private String userNo;
	
	private int isSend;
	
	private String content;
	
	private String title;
	
	private String pushName;
	
	private int isRedTitle;

	private String editName;
	
	private int type;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public int getIsSend() {
		return isSend;
	}

	public void setIsSend(int isSend) {
		this.isSend = isSend;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushName() {
		return pushName;
	}

	public void setPushName(String pushName) {
		this.pushName = pushName;
	}

	public int getIsRedTitle() {
		return isRedTitle;
	}

	public void setIsRedTitle(int isRedTitle) {
		this.isRedTitle = isRedTitle;
	}

	public String getEditName() {
		return editName;
	}

	public void setEditName(String editName) {
		this.editName = editName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
