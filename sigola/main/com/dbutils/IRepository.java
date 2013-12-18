package com.dbutils;

import java.util.List;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： IRepository.java 功能:manage接口类
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-6
 * 
 */

public interface IRepository<T> {
    /**
     * 删除一个实体类
     */
    int remove(T entity);
    /**
     * 获取一个实体类
     */
    T findById(Object id);
    /**
     * 插入一个实体类(如果包含主键，返回结果id，否则返回影响行数)
     */
    int add(T entity);

    /**
     * 更新一个实体类
     * 
     * @param entity
     * @return
     */
    int save(T entity);
    /**
     * 获取分页后代码
     * 
     * @param pageIndex
     * @param pageSize
     * @param querys
     * @return
     */
    PageList<T> findPageList(int pageIndex, int pageSize,
            QueryField... fields);

    List<T> findList(int pageIndex, int pageSize,
            QueryField... fields);
}
