package com.dbutils.conditions;

import com.dbutils.enums.RelationEnum;
 
public class NoEqualContion extends filterContion {
  
    @Override
    public String getSql() { 
        return String.format(" %s <> ?", this.get_QueryField().getFiledName());
    }

    @Override
    public RelationEnum getCurRelation() { 
        return RelationEnum.NoEqual;
    }

}
