package data;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.dataEnum.ConditionEnum;
import data.dataEnum.OrderByEnum;
import data.dataEnum.RelationEnum;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： dataAction.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-28
 * 
 */
public class DataAction {

    enum sqlActionEnum {
        insert, select, update, delete, replace
    }

    private String _tableName = "";

    public void setTable(String tableName) {
        _tableName = tableName;
    }
    public DataAction setTable(BaseDto baseModel) {
        _tableName = baseModel.getTbName();
        return this;
    }

    MysqlHelper _handler = new MysqlHelper();

    private List<QueryField> whereList = new LinkedList<QueryField>();

    public DataAction where(String filedName, Object value) {
        where(filedName, value, ConditionEnum.And, RelationEnum.Equal);
        return this;
    }

    public DataAction where(String filedName, Object value,
            RelationEnum relation) {
        where(filedName, value, ConditionEnum.And, relation);

        return this;
    }

    public DataAction where(String filedName, Object value,
            ConditionEnum condition) {
        where(filedName, value, condition, RelationEnum.Equal);
        return this;
    }

    public DataAction where(String filedName, Object value,
            ConditionEnum condition, RelationEnum relation) {
        QueryField queryField = new QueryField();
        queryField.setCondition(condition);
        queryField.setRelation(relation);
        queryField.setValue(value);
        queryField.setFiledName(filedName);
        whereList.add(queryField);
        return this;
    }
    public DataAction where(QueryField queryField) {
        whereList.add(queryField);
        return this;
    }
    public DataAction where(String whereStr) {
        Pattern pat = Pattern.compile("(s*ands*|s*ors*)");
        Matcher mat = pat.matcher(whereStr.trim().toLowerCase());
        boolean rs = mat.find();
        if (rs && whereStr.trim().indexOf(mat.group(0)) == 0) {
            if (mat.group(0).equals("and")) {
                where(whereStr, "", ConditionEnum.None,
                        RelationEnum.None);
            } else if (mat.group(0).equals("or")) {
                where(whereStr, "", ConditionEnum.None,
                        RelationEnum.None);
            }
        } else {
            where(whereStr, "", ConditionEnum.And, RelationEnum.None);
        }
        return this;
    }
    StringBuilder orderSb = new StringBuilder();
    /**
     * 排序规则
     * 
     * @param filedName
     * @param orderByEnum
     * @return
     */
    public DataAction order(String filedName, OrderByEnum orderByEnum) {
        if (orderSb.length() == 0) {
            orderSb.append(" order by ").append(filedName)
                    .append(createOrder(orderByEnum));
        } else {
            orderSb.append(" , ").append(filedName)
                    .append(createOrder(orderByEnum));
        }
        return this;
    }

    StringBuilder groupsb = new StringBuilder();
    /**
     * group by
     * 
     * @param filedName
     * @return
     */
    public DataAction group(String filedName) {
        if (groupsb.length() == 0) {
            groupsb.append(" group by ").append(filedName);
        } else {
            groupsb.append(" , ").append(filedName);
        }
        return this;
    }

    StringBuilder havingsb = new StringBuilder();
    /**
     * having
     * 
     * @param filedName
     * @return
     */
    public DataAction having(String filedName) {
        if (havingsb.length() == 0) {
            havingsb.append(" having ").append(filedName);
        } else {
            havingsb.append(" , ").append(filedName);
        }
        return this;
    }
    /**
     * 默认降序
     * 
     * @param filedName
     * @return
     */
    public DataAction order(String filedName) {
        return order(filedName, OrderByEnum.Desc);
    }

    private List<ValueField> values = new LinkedList<ValueField>();
    public DataAction setValue(String filedName, Object value) {
        ValueField field = new ValueField();
        field.setField(filedName);
        field.setFieldValue(value);
        values.add(field);
        parms.add(value);
        return this;
    }

    private String _filed = "*";
    /**
     * select 查询的参数
     * 
     * @param filed
     *            demo:"id,name,sex,createdate"
     * @return
     */
    public DataAction setfileds(String filed) {
        if (filed.toString().length() == 0)
            _filed = "*";
        _filed = filed;
        return this;
    }
    private String CreateSql(sqlActionEnum actionEnum) {
        switch (actionEnum) {
            case select :

                // select * from table where.... limit ..group
                // by..having...order by
                String sqlString = "select %s from %s %s %s %s %s %s;";
                return String.format(sqlString, _filed, _tableName,
                        CreateWhere(), limitstr, groupsb.toString(),
                        havingsb.toString(), orderSb.toString());

            case insert : {
                String inserStr = "insert into %s (%s)values(%s);";
                StringBuilder fieldBuilder = new StringBuilder();
                StringBuilder valueBuilder = new StringBuilder();
                for (ValueField value : values) {
                    fieldBuilder.append("," + value.getField());
                    valueBuilder.append(",?");
                }
                return String.format(inserStr, _tableName,
                        fieldBuilder.toString().substring(1),
                        valueBuilder.toString().substring(1));

            }
            case replace : {
                String inserStr = "replace into %s (%s)values(%s);";
                StringBuilder fieldBuilder = new StringBuilder();
                StringBuilder valueBuilder = new StringBuilder();
                for (ValueField value : values) {
                    fieldBuilder.append("," + value.getField());
                    valueBuilder.append(",?");
                }
                return String.format(inserStr, _tableName,
                        fieldBuilder.toString().substring(1),
                        valueBuilder.toString().substring(1));

            }
            case update : {
                // update table set name='' where **
                String updateStr = "update %s set %s %s;";
                StringBuilder fieldBuilder = new StringBuilder();
                for (ValueField value : values) {
                    fieldBuilder
                            .append("," + value.getField() + "=?");

                }
                return String.format(updateStr, _tableName,
                        fieldBuilder.toString().substring(1),
                        CreateWhere());
            }
            case delete : {
                // delete from table where **
                String deleteSql = "delete from %s  %s; ";
                return String.format(deleteSql, _tableName,
                        CreateWhere());
            }
            default :
                break;
        }
        return "";
    }

    List<Object> parms = new LinkedList<Object>();
    private List<Object> createParms() {
        List<Object> objs = new LinkedList<Object>();
        for (int i = 0; i < whereList.size(); i++) {
            QueryField wheref = whereList.get(i);
            if (wheref.getValue() == "") {

            } else {
                objs.add(wheref.getValue());
            }
        }
        return objs;

    }
    private String limitstr = "";
    /**
     * 设置limit %s %s
     * 
     * @param offset
     * @param limit
     * @return
     */
    public DataAction setLimit(Integer offset, Integer limit) {
        limitstr = String.format("limit %s ,%s", offset, limit);
        return this;
    }

    private String CreateWhere() {
        StringBuilder sBuilder = new StringBuilder();
        if (whereList.size() > 0) {
            sBuilder.append(" where 1=1 ");

            DataContion.Compare(whereList);
            parms.addAll(DataContion.getParms());
            sBuilder.append(DataContion.getSqlStr());

        }
        return sBuilder.toString();
    }

    private String createOrder(OrderByEnum OrderByEnum) {
        if (OrderByEnum == data.dataEnum.OrderByEnum.Asc)
            return " asc";
        if (OrderByEnum == data.dataEnum.OrderByEnum.Desc)
            return " desc";
        return " desc";

    }

    public void insert() {
        try {
            resultCode = excute(CreateSql(sqlActionEnum.insert),
                    parms.toArray());

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private int resultCode;
    public int getResultCode() {
        return resultCode;
    }
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
    public void delete() {
        try {
            resultCode = excute(CreateSql(sqlActionEnum.delete),
                    parms.toArray());
            System.out.println(resultCode);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void update() {
        try {
            resultCode = excute(CreateSql(sqlActionEnum.update),
                    parms.toArray());
            System.out.println(resultCode);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public int excute(String sql, Object... parms) {
        try {
            return MysqlHelper.excute(sql, parms);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    public Long getCount() throws SQLException {

        Pattern pat = Pattern.compile("count((.*))");
        Matcher mat = pat.matcher(_filed);
        boolean rs = mat.find();
        if (!rs)
            setfileds("count(1)");
        String sql = CreateSql(sqlActionEnum.select);
        return (Long) MysqlHelper.getSingle(sql, createParms());
    }
    public <T> List<T> getList(Class<T> classbean) {
        try {
            parms.clear();
            String sql = CreateSql(sqlActionEnum.select);
            List<T> dataList = getList(classbean, sql,
                    parms.toArray());
            return dataList;

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> getList(Class<T> classbean, String sql,
            Object... parms) {
        try {
            List<T> dataList = MysqlHelper.queryList(classbean, sql,
                    parms);
            return dataList;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
    public <T> PageList<T> getPageList(Class<T> classbean) {
        try {
            parms.clear();
            String filed = _filed;
            _filed = " count(1) ";
            String sql = CreateSql(sqlActionEnum.select);
            Long count = (Long) MysqlHelper.getSingle(sql,
                    parms.toArray());
            parms.clear();
            if (count == 0)
                return new PageList<T>();
            else {
                _filed = filed;
                sql = CreateSql(sqlActionEnum.select);
                List<T> dataList = MysqlHelper.queryList(classbean,
                        sql, parms.toArray());
                PageList<T> pageList = new PageList<T>();
                pageList.addAll(dataList);
                pageList.setTotalCount(count);
                return pageList;
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
    public <T> T getModel(Class<T> classbean) {
        try {
            String sql = CreateSql(sqlActionEnum.select);
            createParms();
            T modelT = getModel(classbean, sql, parms.toArray());
            return modelT;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
    public <T> T getModel(Class<T> classbean, String sql,
            Object... parms) {
        try {
            T modelT = MysqlHelper.query(classbean, sql, parms);
            return modelT;
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }
}
