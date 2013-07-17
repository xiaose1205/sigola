package data.dataContions;

import java.util.ArrayList;
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
public class NotInContion extends filterContion {
 
    @Override
    public List<Object> getParms() {
        int count = this.get_QueryField().getValue().toString().split("\\,").length;
        StringBuilder sb = new StringBuilder();
        if (count == 1)
            return Arrays.asList(this.get_QueryField().getValue());
        else {
            List<Object> parmsList = new ArrayList<Object>();
            String[] strs = this.get_QueryField().getValue().toString().split("\\,");
            for (int i = 0; i < count; i++) {
                parmsList.add(strs[i]);
            } 
            return parmsList;
        }
    }
    @Override
    public String getSql() {
        int count = this.get_QueryField().getValue().toString().split("\\,").length;
        StringBuilder sb = new StringBuilder();
        if (count == 1)
            sb.append(" = ?");
        else
            for (int i = 0; i < count; i++) {
                if (i == 0)
                    sb.append(" in (?");
                else
                    sb.append(",?");
                if (i == count - 1)
                    sb.append(")");
            }
        return sb.toString();
    }

    @Override
    public RelationEnum getCurRelation() { 
        return RelationEnum.NotIn;
    }

}
