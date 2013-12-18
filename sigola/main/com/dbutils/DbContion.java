package com.dbutils;

import java.util.ArrayList;
import java.util.List;

import com.dbutils.conditions.BetweenContion;
import com.dbutils.conditions.EnqualContion;
import com.dbutils.conditions.InContion;
import com.dbutils.conditions.IsNotNullContion;
import com.dbutils.conditions.IsNullContion;
import com.dbutils.conditions.LargeContion;
import com.dbutils.conditions.LargeThenContion;
import com.dbutils.conditions.LessContion;
import com.dbutils.conditions.LessThenContion;
import com.dbutils.conditions.LikeContion;
import com.dbutils.conditions.LikeLeftContion;
import com.dbutils.conditions.LikeRightContion;
import com.dbutils.conditions.NoEqualContion;
import com.dbutils.conditions.NoneContion;
import com.dbutils.conditions.NotInContion;
import com.dbutils.conditions.filterContion;
import com.dbutils.enums.ConditionEnum;


/**
 * Copyright (C) 2013: UC(优视) All rights reserved
 * 
 * 文件名称： dataContion.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-11
 * 
 */
public class DbContion {

    private static List<filterContion> contions = new ArrayList<filterContion>();

    public static void addContion(filterContion contion) {
        contions.add(contion);
    }

    public static void replaceContion(filterContion contion) {
        for (filterContion filtercontion : contions) {
            if (filtercontion.getCurRelation() == contion
                    .getCurRelation()) {
                filtercontion = contion;
            }
        }
        addContion(contion);
    }

    private static List<Object> parms;
    private static String sqlStr;
    public static List<Object> getParms() {
        return parms;
    }

    public static void setParms(List<Object> parms) {
        DbContion.parms = parms;
    }

    public static String getSqlStr() {
        return sqlStr;
    }

    public static void setSqlStr(String sqlStr) {
        DbContion.sqlStr = sqlStr;
    }

    public static void doContion(QueryField queryfield) {
        for (filterContion filtercontion : contions) {

            if (filtercontion.getCurRelation() == queryfield
                    .getRelation()) {
                filtercontion.set_QueryField(queryfield);
                setParms(filtercontion.getParms());
            }
        }
    }

    /**
     * 通过属性名返回字段名
     * 
     * @param fieldName
     *            属性名
     */
    public static String getColumnName(String fieldName) {
        String[] splitString = fieldName.toString().split(
                "(?<=[a-z])(?=[A-Z])");
        StringBuilder columnName = new StringBuilder();
        for (int i = 0; i < splitString.length; i++) {
            columnName.append(splitString[i].toUpperCase());
            if (i < splitString.length - 1) {
                columnName.append("_");
            }
        }
        return columnName.toString();
    }

    /**
     * 对where的条件组合
     * 
     * @param queryFields
     */

    public static void Compare(List<QueryField> queryFields) {
        List<Object> allparms = new ArrayList<Object>();
        StringBuilder sBuilder = new StringBuilder();
        for (QueryField queryField : queryFields) {
            filterContion filterContion = new EnqualContion();
            switch (queryField.getRelation()) {
            /*
             * 没有关系，适用于直接写where语句
             */
                case None :
                    filterContion = new NoneContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 不等于
                 */
                case NoEqual :
                    filterContion = new NoEqualContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 等于
                 */
                case Equal :
                    filterContion = new EnqualContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 大于
                 */
                case Large :
                    filterContion = new LargeContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 大于等于
                 */
                case LargeThen :
                    filterContion = new LargeThenContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 小于
                 */
                case Less :
                    filterContion = new LessContion()
                            .addFilter(queryField);
                    break;
                /*
                 * 小于等于
                 */
                case LessThen :
                    filterContion = new LessThenContion()
                            .addFilter(queryField);
                    break;
                /*
                 * Like
                 */
                case Like :
                    filterContion = new LikeContion()
                            .addFilter(queryField);
                    break;
                /*
                 * LikeLeft
                 */
                case LikeLeft :
                    filterContion = new LikeLeftContion()
                            .addFilter(queryField);
                    break;
                /*
                 * LikeRight
                 */
                case LikeRight :
                    filterContion = new LikeRightContion()
                            .addFilter(queryField);
                    break;

                /*
                 * Between
                 */
                case Between :
                    filterContion = new BetweenContion()
                            .addFilter(queryField);
                    break;
                /*
                 * in
                 */
                case In :
                    filterContion = new InContion()
                            .addFilter(queryField);
                    break;
                /*
                 * not in
                 */
                case NotIn :
                    filterContion = new NotInContion()
                            .addFilter(queryField);
                    break;
                /*
                 * is null
                 */
                case IsNull :
                    filterContion = new IsNullContion()
                            .addFilter(queryField);
                    break;
                /*
                 * is not null
                 */
                case IsNotNull :
                    filterContion = new IsNotNullContion()
                            .addFilter(queryField);
                    break;
            }
            allparms.addAll(filterContion.getParms());
            sBuilder.append(compareCondition(
                    queryField.getCondition(), filterContion.getSql()));
        }
        setParms(allparms);
        setSqlStr(sBuilder.toString());
    }

    public static String compareCondition(
            ConditionEnum conditionEnum, String relationStr) {
        switch (conditionEnum) {
            case And :
                return String.format(" and %s", relationStr);

            case Or :
                return String.format(" or %s", relationStr);

            case Begin :
                return String.format(" ( %s", relationStr);

            case End :
                return String.format("   %s )", relationStr);

            default :
                return String.format("  %s", relationStr);
        }

    }
}
