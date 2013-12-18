package com.dbutils.conditions;

import java.util.ArrayList;
import java.util.List;

import com.dbutils.enums.RelationEnum;

public class NoneContion extends filterContion {

    @Override
    public RelationEnum getCurRelation() {

        return RelationEnum.None;
    }

    @Override
    public String getSql() { 
        return this.get_QueryField().getFiledName();
    }
    @Override
    public List<Object> getParms() {
        List<Object> objects=new ArrayList<Object>();
        return objects;
    }
}
