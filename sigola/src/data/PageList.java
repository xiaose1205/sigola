package data;

import java.util.ArrayList;

/**
 * Copyright (C) 2013, UC(优视) All rights reserved
 * 
 * 文件名称： pageList.java 功能:
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
}
