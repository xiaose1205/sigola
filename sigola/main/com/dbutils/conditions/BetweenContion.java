package com.dbutils.conditions;

import java.util.ArrayList;
import java.util.List;

import com.dbutils.enums.RelationEnum;




 

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 *  
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-11
 * 
 */
public class BetweenContion extends filterContion {

    @Override
    public List<Object> getParms() {
        List<Object> parmsList = new ArrayList<Object>();
        parmsList.add(this.get_QueryField().getValue());
        parmsList.add(this.get_QueryField().getValue2());
        return parmsList;
    }

    @Override
    public String getSql() {
        return String.format(" %s between ? and ?", this.get_QueryField().getFiledName());
    }

    @Override
    public RelationEnum getCurRelation() {
        return RelationEnum.Between;
    }

}
