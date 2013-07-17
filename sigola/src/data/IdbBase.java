package data;

import java.sql.Connection;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： dbBase.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-24
 * 
 */
public interface IdbBase {

	/**
	 * 设置连接对象
	 * @return
	 */
	Connection getConnection();
}
