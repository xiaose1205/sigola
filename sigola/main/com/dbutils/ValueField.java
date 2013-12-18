package com.dbutils;
/**
 * Copyright (C) 2013, UC(优视)
 * All rights reserved
 * 
 * 文件名称：    valueField.java
 * 功能:     
 * @author    wangjun4
 * @contact   wangjun4@ucweb.com
 * @date      2013-6-9
 *
 */
public class ValueField {

	private String field;
	private Object fieldValue;
	/**
	 * 是否支持参数（如果是否，就原样拼接sql）
	 */
	private boolean isSupParam=true;
	
	public boolean isSupParam() {
        return isSupParam;
    }
    public void setSupParam(boolean isSupParam) {
        this.isSupParam = isSupParam;
    }
    public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
