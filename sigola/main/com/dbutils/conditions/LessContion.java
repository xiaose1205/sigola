package com.dbutils.conditions;

import java.util.Arrays;
import java.util.List;

import com.dbutils.enums.RelationEnum;




 
 
public class LessContion extends filterContion {
 
    @Override
    public List<Object> getParms() {
        return Arrays.asList(this.get_QueryField().getValue());
    }

    @Override
    public String getSql() { 
        return String.format(" %s < ?", this.get_QueryField().getFiledName());
    }

    @Override
    public RelationEnum getCurRelation() { 
        return RelationEnum.Less;
    }

}
