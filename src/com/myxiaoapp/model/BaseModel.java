package com.myxiaoapp.model;

public class BaseModel {

	/**
	 *	 返回状态码
	 */
	public int errno;
	
	/**
	 * 返回状态信息
	 */
	public String message;
	
	public void set(String key, String value) {
		try {
			getClass().getField(key).set(this, value);
		} catch (Exception e) {
		}
	}
	
}
