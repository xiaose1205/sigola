package data;

import java.sql.Connection;

/**
 * Copyright (C) 2013, UC(����) All rights reserved
 * 
 * �ļ����ƣ� dbBase.java ����:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-24
 * 
 */
public interface IdbBase {

	/**
	 * �������Ӷ���
	 * @return
	 */
	Connection getConnection();
}
