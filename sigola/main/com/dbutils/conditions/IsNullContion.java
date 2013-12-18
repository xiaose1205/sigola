package com.dbutils.conditions;

import java.util.ArrayList;
import java.util.List;

import com.dbutils.enums.RelationEnum;
 
public class IsNullContion extends filterContion {

    @Override
    public List<Object> getParms() {
        return new ArrayList<Object>();
    }

    @Override
    public String getSql() {
        return String.format(" %s is null", this.get_QueryField().getFiledName());
    }

    @Override
    public RelationEnum getCurRelation() {
        return RelationEnum.IsNull;
    }

}
