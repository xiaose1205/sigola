package com.dbutils;

import com.dbutils.enums.*;

 

 

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： queryField.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-7
 * 
 */
public class QueryField {
    public QueryField() {
        setRelation(RelationEnum.Equal);
        setCondition(ConditionEnum.And);
    }
    // / <summary>
    // / 操作的列名
    // / </summary>
    private String FiledName;
    // / <summary>
    // / 第一个参数
    // / </summary>
    private Object Value;
    public String getFiledName() {
        return FiledName;
    }
    public void setFiledName(String filedName) {
        FiledName = filedName;
    }
    public Object getValue() {
        return Value;
    }
    public void setValue(Object value) {
        Value = value;
    }
    public Object getValue2() {
        return Value2;
    }
    public void setValue2(Object value2) {
        Value2 = value2;
    }
    public RelationEnum getRelation() {
        return Relation;
    }
    public void setRelation(RelationEnum relation) {
        Relation = relation;
    }
    public ConditionEnum getCondition() {
        return Condition;
    }
    public void setCondition(ConditionEnum condition) {
        Condition = condition;
    }
    public boolean isIsWhereField() {
        return IsWhereField;
    }
    public void setIsWhereField(boolean isWhereField) {
        IsWhereField = isWhereField;
    }
    // / <summary>
    // / 第二个参数，用于betweent
    // / </summary>
    private Object Value2;
    // / <summary>
    // / 常用的关系，〉〈 in != like 等
    // / </summary>
    private RelationEnum Relation;
    // / <summary>
    // / 语句链接关系and or
    // / </summary>
    private ConditionEnum Condition;
    // / <summary>
    // / 是否是where条件,否则value值为普通的sql语句，例如：and username='wangjun'
    // / </summary>
    private boolean IsWhereField;
}