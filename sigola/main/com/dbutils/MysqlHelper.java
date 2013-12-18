package com.dbutils;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.dbutils.IdbBase;
import com.dbutils.dbutilsext.*;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： mysqlHelper.java 功能:(将dbutil的功能抽离，以后如果不用dbutil只需更改本文件的对应接口)
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-28
 * 
 */
public class MysqlHelper {

    public static IdbBase idbBase;
    /**
     * 执行SQL语句的类
     */
    private final static QueryRunner queryRunner = new QueryRunner();

    public static Connection getConnection() {

        return idbBase.getConnection();
    }

    public static void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public static void close(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public static void close(Statement stmt) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
    }

    public static void commitAndClose(Connection conn)
            throws SQLException {
        if (conn != null) {
            try {
                conn.commit();
            } finally {
                conn.close();
            }
        }
    }

    // http://www.cnblogs.com/beijiguangyong/archive/2011/12/10/2302737.html

    /**
     * 将ResultSet中某一列的数据存成List，List中存放的是Object对象
     */
    private final static ColumnListHandler columnListHandler = new ColumnListHandler() {
        @Override
        protected Object handleRow(ResultSet rs) throws SQLException {
            Object obj = super.handleRow(rs);
            if (obj instanceof BigInteger)
                return ((BigInteger) obj).longValue();
            return obj;
        }
    };

    /**
     * 将ResultSet中一条记录的其中某一列的数据存成Object
     */
    private final static ScalarHandler scalarHandler = new ScalarHandler() {
        @Override
        public Object handle(ResultSet rs) throws SQLException {
            Object obj = super.handle(rs);
            if (obj instanceof BigInteger)
                return ((BigInteger) obj).longValue();
            return obj;
        }
    };

    /**
     * 原始类列表
     */
    private final static List<Class<?>> PrimitiveClasses = new ArrayList<Class<?>>() {

        private static final long serialVersionUID = -1280108577998366966L;

        {
            add(Long.class);
            add(Integer.class);
            add(String.class);
            add(java.util.Date.class);
            add(java.sql.Date.class);
            add(java.sql.Timestamp.class);
        }
    };

    private final static boolean isPrimitive(Class<?> clzz) {
        // Class.isPrimitive()判定指定的 Class 对象是否表示一个基本类型
        // PrimitiveClasses.contains(clzz)是否存在与原始类列表中
        return clzz.isPrimitive() || PrimitiveClasses.contains(clzz);
    }
    /**
     * 查询某个对象
     * 
     * @param <T>
     * @param beanClass
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     * @throws DBException
     */
    public static <T> T query(Class<T> beanClass, String sql,
            Object... params) throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        return (T) queryRunner.query(getConnection(), sql,
                isPrimitive(beanClass)
                        ? scalarHandler
                        : new BeanHandler<T>(beanClass,
                                new BasicRowProcessor(
                                        new StrategyBeanProcessor(
                                                new HumpMatcher()))),
                params);
    }

    /**
     * 对象列表查询
     * 
     * @param <T>
     * @param beanClass
     * @param sql
     * @param params
     * @return
     * @throws DBException
     */

    @SuppressWarnings("unchecked")
    public static <T> List<T> queryList(Class<T> beanClass,
            String sql, Object... params) throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        return (List<T>) queryRunner.query(getConnection(), sql,
                isPrimitive(beanClass)
                        ? columnListHandler
                        : new BeanListHandler<T>(beanClass,
                                new BasicRowProcessor(
                                        new StrategyBeanProcessor(
                                                new HumpMatcher()))),
                params);

    }

    /**
     * 执行统计查询语句，语句的执行结果必须只返回一个数值
     * 
     * @param sql
     * @param params
     * @return
     * @throws DBException
     */
    public static long stat(String sql, Object... params)
            throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        Number num = (Number) queryRunner.query(getConnection(), sql,
                scalarHandler, params);
        return (num != null) ? num.longValue() : -1;
    }
    /**
     * 执行INSERT/UPDATE/DELETE语句
     * 
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int excute(String sql, Object... params)
            throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        if (sql.toLowerCase().indexOf("insert") == -1) {
            return queryRunner.update(getConnection(), sql, params);
        } else { 
            return queryRunner.update(getConnection(), sql, params);

        }
    }
    /**
     * insert into 并返回最后的响应的id
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int excuteInsetIdi(String sql, Object... params)
            throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        Connection conn = getConnection();
        QueryRunner qr = new QueryRunner();
        PreparedStatement pst = conn.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS);
        qr.fillStatement(pst, params);
        pst.execute();
        ResultSet rs = pst.getGeneratedKeys();
        return rs.next() ? rs.getInt(1) : 0;
    }
    /**
     * 批量执行指定的SQL语句
     * 
     * @param sql
     * @param params
     * @return
     * @throws DBException
     */
    public static int[] batch(String sql, Object[][] params)
            throws SQLException {
        try {
            printIn(sql);
            return queryRunner.batch(getConnection(), sql, params);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
    /**
     * 返回执行后的结果单条
     * 
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static Object getSingle(String sql, Object... params)
            throws SQLException {
        printIn(sql);
        printIn("params length:" + params.length);
        for (int i = 0; i < params.length; i++) {
            printIn("params" + i + ":" + params[i]);
        }
        Object object = queryRunner.query(getConnection(), sql,
                scalarHandler, params);
        return object;
    }
    public static void printIn(Object sql) {
        if (isLogSql())
            System.out.println(sql);
    }
    private static boolean LogSql = false;

    public static boolean isLogSql() {
        return idbBase.isLogSql();
    }
}