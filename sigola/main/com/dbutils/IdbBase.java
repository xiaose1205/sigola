package com.dbutils;

import java.sql.Connection;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * dbBase.java
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-24
 * 
 */
public interface IdbBase {

    /**
     * 设置连接对象
     * 
     * @return
     */
    Connection getConnection();

    boolean isLogSql();
    /**
     * 获取唯一的标志
     * @return
     */
    int getUnique();
}
