package com.dbutils;

import java.util.ArrayList;

/**
 * Copyright (C) 2013, UC All rights reserved
 * 
 * pageList.java 功能：生成结果list，并包含了totalCount，与list使用一样。
 * 
 * @author wangjun4
 * @contact wangjun4@ucweb.com
 * @date 2013-5-29
 * 
 */
public class PageList<E> extends ArrayList<E> {

    private Long totalCount = (long) 0;

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = Long.parseLong(totalCount + "");
    }
}
