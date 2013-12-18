package com.dbutils.conditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dbutils.enums.RelationEnum;

public class NotInContion extends filterContion {

    @Override
    public List<Object> getParms() {
        int count = this.get_QueryField().getValue().toString()
                .split("\\,").length;
        if (count == 1)
            return Arrays.asList(this.get_QueryField().getValue());
        else {
            List<Object> parmsList = new ArrayList<Object>();
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
            sb.append(" = ?");
        else
            for (int i = 0; i < count; i++) {
                if (i == 0)
                    sb.append(" not in (?");
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
