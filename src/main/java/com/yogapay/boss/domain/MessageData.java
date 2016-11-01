/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.domain;

/**
 *
 * @author zeng
 */
public class MessageData {

	public String content;
	public int msgCount;
	public int msgAndroid;
	public Object data;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public int getMsgAndroid() {
		return msgAndroid;
	}

	public void setMsgAndroid(int msgAndroid) {
		this.msgAndroid = msgAndroid;
	}
}
