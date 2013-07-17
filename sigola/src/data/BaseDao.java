package data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.lang.reflect.ParameterizedType;
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

    public DataAction Cast() {
        DataAction dataAction = new DataAction();
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
        clazz = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public BaseDao(T baseModel) {
        clazz = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * 删除一个实体类
     */
    @Override
    public int Remove(T entity) {
        DataAction daction = new DataAction();
        daction.setTable(entity.getTbName());
        Map<String, String> valueMap = entity.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.where(entry.getKey().toString(), entry.getValue());
        }
        daction.delete();
        return daction.getResultCode();
    }
    /**
     * 获取一个实体类
     */
    @Override
    public T FindById(Object id) {
        DataAction daction = new DataAction();
        daction.where("id", id);
        return daction.getModel(clazz);
    }
    /**
     * 插入一个实体类
     */
    @Override
    public int Add(T entity) {
        DataAction daction = new DataAction();
        daction.setTable(entity.getTbName());
        Map<String, String> valueMap = entity.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.setValue(entry.getKey().toString(), entry.getValue());
        }
        daction.insert();
        return daction.getResultCode();
    }
    /**
     * 插入一个实体类
     */
    @Override
    public int AddList(List<T> entities) {
        if (entities.size() == 0)
            return 0;
        return 0;
    }
    /**
     * 更新一个实体类
     * 
     * @param entity
     * @return
     */
    @Override
    public int Save(T entity) {

        DataAction daction = new DataAction();
        daction.setTable(entity.getTbName());
        Map<String, String> valueMap = entity.toMap();
        Iterator it = valueMap.entrySet().iterator();
        for (; it.hasNext();) {
            Map.Entry entry = (Entry) it.next();
            daction.setValue(entry.getKey().toString(), entry.getValue());
        }
        daction.where(entity.getKeyName(), entity.getId());
        daction.update();
        return daction.getResultCode();
    }
    /**
     * 获取分页后代码
     * 
     * @param pageIndex
     * @param pageSize
     * @param querys
     * @return
     */
    @Override
    public PageList<T> FindPageList(int pageIndex, int pageSize, QueryField... fields) {
        DataAction daction = new DataAction();
        daction.setTable(get_baseModel());
        daction.setLimit(pageIndex * pageSize, pageSize);
        for (QueryField queryField : fields) {
            daction.where(queryField);
        }
        return daction.getPageList(clazz);
    }
    /**
     * 获取对象的list
     */
    @Override
    public List<T> FindList(int pageIndex, int pageSize, QueryField... fields) {
        DataAction daction = new DataAction();
        daction.setLimit(pageIndex * pageSize, pageSize);
        daction.setTable(get_baseModel());
        for (QueryField queryField : fields) {
            daction.where(queryField);
        }
        return daction.getList(clazz);
    }
}