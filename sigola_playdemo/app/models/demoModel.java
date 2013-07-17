package models;

import groovy.lang.Newify;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import data.BaseDto;


/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： demoModel.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-9
 * 
 */
public class demoModel extends BaseDto {
	public demoModel()
	{
		this.setTbName("demo");
	}

	private Integer id;

	private String userName;
	private String userPwd;
	private Date createDate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(Integer id) {
	 
		this.id = id;
	} 
	public Integer getId() { 
		
		return  this.id;
	}

	@Override
	public Map<String, String> toMap() {

		Map<String, String> map = new HashMap<String, String>();
		if (this.id != null) {
			map.put("id", this.id.toString());
		}
		if (this.userName != null) {
			map.put("userName", this.userName);
		}
		if (this.userPwd != null) {
			map.put("userPwd", this.userPwd);
		}
		return map;
	}

}
