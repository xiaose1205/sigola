package com.dbutils.conditions;

import java.util.Arrays;
import java.util.List;

import com.dbutils.QueryField;
import com.dbutils.enums.RelationEnum;

 
 
 
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
