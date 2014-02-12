package com.dbutils;

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
	public abstract Long getId();

	/**
	 * 表名
	 */
	private transient String tbName;
	/**
	 * 主键名
	 */
	private transient String keyName = "id";

	private String createDateStr;

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

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

	public abstract Map<String, Object> toMap();

	/**
	 * 操作数据库的对应的数据库的顺序,默认为第一个数据库
	 */
	private transient int dbIndex = 0;

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}
	

}