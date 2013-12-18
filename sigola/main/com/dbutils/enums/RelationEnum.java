package com.dbutils.enums;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： RelationEnum.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-28
 * 
 */

public enum RelationEnum {
    /*
     * 没有关系，适用于直接写where语句
     */
    None,
    /*
     * 不等于
     */
    NoEqual,
    /*
     * 等于
     */
    Equal,
    /*
     * 大于
     */
    Large,
    /*
     * 大于等于
     */
    LargeThen,
    /*
     * 小于
     */
    Less,
    /*
     * 小于等于
     */
    LessThen,
    /*
     * Like
     */
    Like,
    /*
     * Like
     */
    LikeLeft,
    /*
     * Like
     */
    LikeRight,
     
    /*
     *
     */
    Between,
    /*
     * in
     */
    In,
    /*
     * not in
     */
    NotIn,
    /*
     * is null
     */
    IsNull,
    /*
     * is not null
     */
    IsNotNull;
    
    public String getName()
    {
        return this.name();
    }
}
