package data.dataContions;

import java.util.Arrays;
import java.util.List;

import data.QueryField;
import data.dataEnum.RelationEnum;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： EnqualContion.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-11
 * 
 */
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
