package com.dbutils;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 
import com.dbutils.common.DateUtil;
import com.dbutils.enums.*;

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
public class DbAction {

	public enum sqlActionEnum {
		insert, select, update, delete, replace, insertidentity
	}

	private String _tableName = "";

	public DbAction setTable(String tableName) {
		_tableName = tableName;
		return this;
	}

	public DbAction setTable(BaseDto baseModel) {
		_tableName = baseModel.getTbName();
		return this;
	}

	private MysqlHelper _handler = new MysqlHelper();

	private List<QueryField> whereList = new LinkedList<QueryField>();

	/**
	 * 设置where条件
	 * 
	 * @param filedName
	 * @param value
	 * @return
	 */
	public DbAction setWhere(String filedName, Object value) {
		setWhere(filedName, value, ConditionEnum.And, RelationEnum.Equal);
		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param filedName
	 * @param value
	 * @param relation
	 *            in做了优化，自动将一条数据拼接为等于
	 * @return
	 */
	public DbAction setWhere(String filedName, Object value,
			RelationEnum relation) {
		setWhere(filedName, value, ConditionEnum.And, relation);

		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param filedName
	 * @param value
	 * @param condition
	 * @return
	 */
	public DbAction setWhere(String filedName, Object value,
			ConditionEnum condition) {
		setWhere(filedName, value, condition, RelationEnum.Equal);
		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param filedName
	 * @param value
	 * @param condition
	 *            and,or
	 * @param relation
	 *            in,=,!=等条件
	 * @return
	 */
	public DbAction setWhere(String filedName, Object value,
			ConditionEnum condition, RelationEnum relation) {
		QueryField queryField = new QueryField();
		queryField.setCondition(condition);
		queryField.setRelation(relation);
		if (value != null && value.getClass() == Date.class)
			queryField.setValue(DateUtil.tranDate((Date) value));
		else
			queryField.setValue(value);
		queryField.setFiledName(filedName);
		whereList.add(queryField);
		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param queryField
	 * @return
	 */
	public DbAction setWhere(QueryField queryField) {
		whereList.add(queryField);
		return this;
	}

	/**
	 * 设置where条件
	 * 
	 * @param whereStr
	 *            会自动加入and,例如："id=1" or " and id=1"
	 * @return
	 */
	public DbAction setWhere(String whereStr) {
		Pattern pat = Pattern.compile("(s*ands*|s*ors*)");
		Matcher mat = pat.matcher(whereStr.trim().toLowerCase());
		boolean rs = mat.find();
		if (rs && whereStr.trim().indexOf(mat.group(0)) == 0) {
			if (mat.group(0).equals("and")) {
				setWhere(whereStr, "", ConditionEnum.None, RelationEnum.None);
			} else if (mat.group(0).equals("or")) {
				setWhere(whereStr, "", ConditionEnum.None, RelationEnum.None);
			}
		} else {
			setWhere(whereStr, "", ConditionEnum.And, RelationEnum.None);
		}
		return this;
	}

	StringBuilder orderSb = new StringBuilder();

	/**
	 * 默认降序
	 * 
	 * @param filedName
	 * @return
	 */
	public DbAction setOrder(String filedName) {
		orderSb.append(" order by ").append(filedName);
		return this;
	}

	/**
	 * 排序规则
	 * 
	 * @param filedName
	 * @param orderByEnum
	 * @return
	 */
	public DbAction setOrder(String filedName, OrderByEnum orderByEnum) {
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
	public DbAction setGroup(String filedName) {
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
	public DbAction setHaving(String filedName) {
		if (havingsb.length() == 0) {
			havingsb.append(" having ").append(filedName);
		} else {
			havingsb.append(" , ").append(filedName);
		}
		return this;
	}

	private List<ValueField> values = new LinkedList<ValueField>();

	/**
	 * 设置insert 或者update的value
	 * 
	 * @param filedName
	 * @param value
	 * @return
	 */
	public DbAction setValue(String filedName, Object value) {
		ValueField field = new ValueField();
		field.setField(filedName);
		if (value.getClass() == Date.class) {
			field.setFieldValue(DateUtil.tranDate((Date) value));
			params.add(DateUtil.tranDate((Date) value));
		} else {
			field.setFieldValue(value);
			params.add(value);
		}
		values.add(field);

		return this;
	}

	/**
	 * 设置insert 或者update的value
	 * 
	 * @param filedName
	 * @param value
	 * @return
	 */
	public DbAction setValue(String filedName, Object value, boolean isSupParam) {
		ValueField field = new ValueField();
		field.setField(filedName);
		field.setSupParam(isSupParam);
		if (value.getClass() == Date.class) {
			field.setFieldValue(DateUtil.tranDate((Date) value));

		} else {
			field.setFieldValue(value);
		}
		values.add(field);
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
	public DbAction setFileds(String filed) {
		if (filed.toString().length() == 0)
			_filed = "*";
		_filed = filed;
		return this;
	}

	public String createSql(sqlActionEnum actionEnum) {
		switch (actionEnum) {
		case select:

			// select * from table where.... limit ..group
			// by..having...order by
			String sqlString = "select %s from %s %s %s %s %s %s;";
			return String.format(sqlString, _filed, _tableName, CreateWhere(),
					groupsb.toString(), havingsb.toString(),
					orderSb.toString(), limitstr);

		case insert: {
			String inserStr = "insert into %s (%s)values(%s);";
			StringBuilder fieldBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			for (ValueField value : values) {
				fieldBuilder.append(",").append(value.getField());
				if (value.isSupParam())
					valueBuilder.append(",?");
				else {
					valueBuilder.append(",").append(value.getFieldValue());
				}
			}
			return String.format(inserStr, _tableName, fieldBuilder.toString()
					.substring(1), valueBuilder.toString().substring(1));

		}
		case replace: {
			String inserStr = "replace into %s (%s)values(%s);";
			StringBuilder fieldBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			for (ValueField value : values) {
				fieldBuilder.append(",").append(value.getField());
				if (value.isSupParam())
					valueBuilder.append(",?");
				else {
					valueBuilder.append(",").append(value.getFieldValue());
				}
			}
			return String.format(inserStr, _tableName, fieldBuilder.toString()
					.substring(1), valueBuilder.toString().substring(1));

		}
		case update: {
			// update table set name='' where **
			String updateStr = "update %s set %s %s;";
			StringBuilder fieldBuilder = new StringBuilder();
			for (ValueField value : values) {
				if (value.isSupParam())
					fieldBuilder.append(",").append(value.getField())
							.append("=?");
				else {
					fieldBuilder.append(",").append(value.getField())
							.append("=").append(value.getFieldValue());
				}

			}
			return String.format(updateStr, _tableName, fieldBuilder.toString()
					.substring(1), CreateWhere());
		}
		case delete: {
			// delete from table where **
			String deleteSql = "delete from %s  %s; ";
			return String.format(deleteSql, _tableName, CreateWhere());
		}
		case insertidentity: {
			String inserStr = "insert into %s (%s)values(%s);select @@identity;";
			StringBuilder fieldBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			for (ValueField value : values) {
				fieldBuilder.append("," + value.getField());
				valueBuilder.append(",?");
			}
			return String.format(inserStr, _tableName, fieldBuilder.toString()
					.substring(1), valueBuilder.toString().substring(1));
		}
		default:
			break;
		}
		return "";
	}

	List<Object> params = new LinkedList<Object>();

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
	 *            使用pageindex*pagesize.
	 * @param limit
	 * @return
	 */
	public DbAction setLimit(int start, int limit) {
		if (start == -1 || limit == -1 || limit == 0)
			return this;
		limitstr = String.format("limit %s ,%s", start, limit);
		return this;
	}

	private String CreateWhere() {
		StringBuilder sBuilder = new StringBuilder();
		if (whereList.size() > 0) {
			sBuilder.append(" where 1=1 ");

			DbContion.Compare(whereList);
			params.addAll(DbContion.getParms());
			sBuilder.append(DbContion.getSqlStr());

		}
		return sBuilder.toString();
	}

	private String createOrder(OrderByEnum OrderByEnum) {
		if (OrderByEnum == OrderByEnum.Asc)
			return " asc";
		if (OrderByEnum == OrderByEnum.Desc)
			return " desc";
		return " desc";
	}

	/**
	 * 执行特定的条件 ORM
	 * 
	 * @param actionEnum
	 * @return
	 */
	public int excute(sqlActionEnum actionEnum) {
		try {
			if (actionEnum == sqlActionEnum.insertidentity)
				try {
					resultCode = MysqlHelper.excuteInsetIdi(
							createSql(actionEnum), params.toArray());
				} catch (SQLException e) {

					e.printStackTrace();
				}
			else
				resultCode = excute(createSql(actionEnum), params.toArray());

		} catch (Exception e) {
			resultCode = 0;
			e.printStackTrace();
		}
		return resultCode;
	}

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	private void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * 执行sql，可带参数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int excute(String sql, Object... params) {
		try {
			return MysqlHelper.excute(sql, params);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回执行后的结果单条
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object getSingle(String sql, Object... params) {
		try {
			return MysqlHelper.getSingle(sql, params);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回结果数目 ORM
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long getCount() {

		Pattern pat = Pattern.compile("count((.*))");
		Matcher mat = pat.matcher(_filed);
		limitstr = "";
		orderSb = new StringBuilder();
		boolean rs = mat.find();
		if (!rs || _filed.indexOf(",") > -1)
			setFileds("count(1)");
		String sql = createSql(sqlActionEnum.select);

		return (Long) getSingle(sql, params.toArray());

	}

	/**
	 * 获取list ORM
	 * 
	 * @param classbean
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> getList(Class<T> classbean) {
		try {
			params.clear();
			String sql = createSql(sqlActionEnum.select);
			List<T> dataList = getList(classbean, sql, params.toArray());
			return dataList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取list
	 * 
	 * @param classbean
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> getList(Class<T> classbean, String sql, Object... params) {
		try {
			List<T> dataList = MysqlHelper.queryList(classbean, sql, params);
			return dataList;

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取带count的list ORM
	 * 
	 * @param classbean
	 * @return
	 */
	public <T> PageList<T> getPageList(Class<T> classbean) {
		try {

			String filed = _filed;
			_filed = " count(1) ";
			String limit = limitstr;
			limitstr = "";
			String order = orderSb.toString();
			orderSb = new StringBuilder();
			String sql = createSql(sqlActionEnum.select);
			Object[] parObjects = params.toArray();
			Long count = (Long) getSingle(sql, parObjects);
			if (count == 0)
				return new PageList<T>();
			else {
				limitstr = limit;
				_filed = filed;
				sql = createSql(sqlActionEnum.select);
				List<T> dataList = MysqlHelper.queryList(classbean, sql,
						parObjects);
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

	/**
	 * 获取一个model ORM
	 * 
	 * @param classbean
	 * @return
	 */
	public <T> T getModel(Class<T> classbean) {
		try {
			String sql = createSql(sqlActionEnum.select);
			createParms();
			T modelT = getModel(classbean, sql, params.toArray());
			return modelT;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取一个model
	 * 
	 * @param classbean
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> T getModel(Class<T> classbean, String sql, Object... params) {
		try {
			T modelT = MysqlHelper.query(classbean, sql, params);
			return modelT;
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
	}
}
