package data.dataContions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.DataContion;
import data.QueryField;
import data.dataEnum.RelationEnum;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： filterContion.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-11
 * 
 */
public abstract class filterContion {

    private QueryField _QueryField;
    public QueryField get_QueryField() {
        return _QueryField;
    }

    public void set_QueryField(QueryField _QueryField) {
        this._QueryField = _QueryField;
    }

    public filterContion addFilter(QueryField queryfield) {
        set_QueryField(queryfield);
        return this;
    }

    public abstract RelationEnum getCurRelation();

    public List<Object> getParms() {
        return Arrays.asList(this.get_QueryField().getValue());
    }
    public abstract String getSql();

}
