package com.dbutils.conditions;

import java.util.Arrays;
import java.util.List;

import com.dbutils.enums.RelationEnum;




 
 
public class LikeLeftContion extends filterContion {
 
    @Override
    public List<Object> getParms() {
        Object object = "%" + this.get_QueryField().getValue();
        return Arrays.asList(object);
    }

    @Override
    public String getSql() {
        return String.format(" %s like ?", this.get_QueryField().getFiledName());
    }


    @Override
    public RelationEnum getCurRelation() { 
        return RelationEnum.LikeLeft;
    }

}
