package com.chnghx.common.base;

import java.io.Serializable;

public class APIResult<V> implements Serializable{
	
	private static final long serialVersionUID = -7925016012331196255L;
	/**返回状态
	 * */
	private int status ;
	/**
	 * 返回具体执行信息
	 */
	private String message;
	/**
	 * V V根据在new APIResult<V>的时候，如果V是存储一个User对象，那么就new APIResult<User>();
	 * 可以达到不用强转，直接Get即可获取。
	 */
	private V result ;

	public APIResult() {
		super();
	}
	public APIResult(int status,String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public APIResult(int status,String message,V result) {
		super();
		this.status = status;
		this.message = message;
		this.result = result ;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public V getResult() {
		return result;
	}
	public void setResult(V result) {
		this.result = result;
	}

}
