package data;

import java.util.Map;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： baseModel.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-28
 * 
 */
public abstract class BaseDto {

	/**
	 * 自动获取到id的值
	 * 
	 * @return
	 */
	public abstract Integer getId();
 
	/**
	 * 表名
	 */
	private String tbName;
	/**
	 * 主键名
	 */
	private String keyName="id";

	public String getTbName() {
		return tbName;
	}

	public void setTbName(String tbName) {
		this.tbName = tbName;
	} 
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public abstract Map<String, String> toMap();

	/**
	 * json格式
	 */
	@Override
	public String toString() {
		return "";
	}
}
