package com.dbutils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.lang.reflect.ParameterizedType;

import com.dbutils.DbAction.sqlActionEnum;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： baseAction.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-6-6
 * 
 */

public class BaseDao<T extends BaseDto> implements IRepository<T> {
    // DbAction dataAction = null;

    /**
     * 操作当前的操作<T>实体对象的逻辑
     * 
     * @return
     */
    public DbAction Cast() {
        // if (dataAction == null)
        DbAction dataAction = new DbAction();
        dataAction.setTable(get_baseModel());
        return dataAction;
    }

    private T _baseModel;

    public T get_baseModel() {
        if (_baseModel != null)
            return _baseModel;
        try {
            _baseModel = clazz.newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return _baseModel;
    }

    public void set_baseModel(T _baseModel) {
        this._baseModel = _baseModel;
    }
    private Class<T> clazz;
    public BaseDao() {
        super();
        try {
            clazz = (Class<T>) ((ParameterizedType) super.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            clazz = (Class<T>) ((ParameterizedType) super.getClass()
                    .getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }

    }
    public BaseDao(T baseModel) {
        try {
            clazz = (Class<T>) ((ParameterizedType) super.getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            clazz = (Class<T>) ((ParameterizedType) super.getClass()
                    .getSuperclass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }

    }

    @Override
    public int remove(T info) {
        DbAction daction = new DbAction();
        daction.setTable(info.getTbName());
        Map<String, Object> valueMap = info.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.setWhere(entry.getKey().toString(),
                    entry.getValue());
        }
        daction.excute(sqlActionEnum.delete);

        return daction.getResultCode();
    }

    @Override
    public T findById(Object id) {
        DbAction daction = new DbAction();
        daction.setWhere("id", id);
        return daction.getModel(clazz);
    }

    @Override
    public int add(T info) {
        DbAction daction = new DbAction();
        daction.setTable(info.getTbName());
        Map<String, Object> valueMap = info.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.setValue(entry.getKey().toString(),
                    entry.getValue());
        }
        daction.excute(sqlActionEnum.insertidentity);
        
        return daction.getResultCode();
    }

    @Override
    public int save(T info) {
        DbAction daction = new DbAction();
        daction.setTable(info.getTbName());
        Map<String, Object> valueMap = info.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.setValue(entry.getKey().toString(),
                    entry.getValue());
        }
        daction.setWhere(info.getKeyName(), info.getId());
        daction.excute(sqlActionEnum.update);
        return daction.getResultCode();
    }

    @Override
    /**
     * 加载带有count的列表   start,limit为-1加载全部
     */
    public PageList<T> findPageList(int start, int limit,
            QueryField... fields) {
        DbAction daction = new DbAction();
        daction.setTable(get_baseModel());
        daction.setLimit(start, limit);
        for (QueryField queryField : fields) {
            daction.setWhere(queryField);
        }
        return daction.getPageList(clazz);
    }

    @Override
    /**
     * 加载列表   start,limit为-1加载全部
     */
    public List<T> findList(int start, int limit,
            QueryField... fields) {
        DbAction daction = new DbAction();
        daction.setLimit(start, limit);
        daction.setTable(get_baseModel());
        for (QueryField queryField : fields) {
            daction.setWhere(queryField);
        }
        return daction.getList(clazz);
    }

}