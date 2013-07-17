package data.dataContions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import data.dataEnum.RelationEnum;
/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： NoneContion.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-16
 * 
 */
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
        if (this.get_QueryField().getValue() != null)
            objects.add(this.get_QueryField().getValue());
        if (this.get_QueryField().getValue2() != null)
            objects.add(this.get_QueryField().getValue2());
        return objects;
    }
}
