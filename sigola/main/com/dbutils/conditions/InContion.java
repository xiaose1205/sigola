package com.dbutils.conditions;

import java.util.ArrayList;
import java.util.List;

import com.dbutils.enums.RelationEnum;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称 EnqualContion.java 功能:
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-7-11
 * 
 */
public class InContion extends filterContion {

    @Override
    public List<Object> getParms() {
        int count = this.get_QueryField().getValue().toString()
                .split("\\,").length;
        List<Object> parmsList = new ArrayList<Object>();
        if (count == 1) {
            parmsList.add(this.get_QueryField().getValue().toString()
                    .split("\\,")[0]);
            return parmsList;

        } else {

            String[] strs = this.get_QueryField().getValue()
                    .toString().split("\\,");
            for (int i = 0; i < count; i++) {
                parmsList.add(strs[i]);
            }
            return parmsList;
        }
    }
    @Override
    public String getSql() {
        int count = this.get_QueryField().getValue().toString()
                .split("\\,").length;
        StringBuilder sb = new StringBuilder();
        if (count == 1)
            sb.append(this.get_QueryField().getFiledName()).append(
                    " = ?");
        else
            for (int i = 0; i < count; i++) {
                if (i == 0)
                    sb.append(this.get_QueryField().getFiledName())
                            .append(" in (?");
                else
                    sb.append(",?");
                if (i == count - 1)
                    sb.append(")");
            }
        return sb.toString();
    }

    @Override
    public RelationEnum getCurRelation() {
        return RelationEnum.In;
    }

}
