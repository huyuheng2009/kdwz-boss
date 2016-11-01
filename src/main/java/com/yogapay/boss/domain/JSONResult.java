/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yogapay.boss.domain;

/**
 *
 * @author zeng
 */
public class JSONResult {

	private int errorCode;
	private String message;
	private Object value;

	public JSONResult() {
	}

	public JSONResult(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public JSONResult(int errorCode, String message, Object value) {
		this.errorCode = errorCode;
		this.message = message;
		this.value = value;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
