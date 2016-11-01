package com.yogapay.boss.domain;


/**
 * 页面对象
 *
 */
public class Result<T> {

	/**
	 * 数据正文
	 */
	private T data = null;
	
	/**
	 * 操作状态
	 */
	private boolean flag = false;
	
	/**
	 * 提示信息
	 */
	private String prompt = null;
	
	/**
	 * 状态值
	 * 0 - 正常
	 * -1 - 需要登录
	 */
	private int status = 0;
	
	/**
	 * 状态值
	 * 0 - 正常
	 * -1 - 需要登录
	 * -2 - 无权限操作
	 * more......
	 * @return int
	 */
	public int getStatus() {
		return status;
	}

	public Result setStatus(int status) {
		this.status = status;
		return this;
	}

	/**
	 * 数据块
	 * @return Object
	 */
	public T getData() {
		return data;
	}

	public Result setData(T data) {
		this.data = data;
		return this;
	}

	/**
	 * 操作返回状态，操作成功为true
	 * @return boolean
	 */
	public boolean isFlag() {
		return flag;
	}

	public Result setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}

	/**
	 * 提示信息
	 * @return String
	 */
	public String getPrompt() {
		return prompt;
	}

	public Result setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
}
