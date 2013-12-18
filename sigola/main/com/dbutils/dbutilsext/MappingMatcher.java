package com.dbutils.dbutilsext;

import java.util.HashMap;
import java.util.Map;

 
public class MappingMatcher implements Matcher {
	
	private Map<String, String> map = null;
	
	public MappingMatcher(String [][] mapping){
		if (mapping == null)
			throw new IllegalArgumentException();
		
		map = new HashMap<String, String>();
		for (int i = 0; i < mapping.length; i++){
			String columnName = mapping[i][0];
			if (columnName != null)
				map.put(columnName.toUpperCase(), mapping[i][1]);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.yang.commons.dbutils.Matcher#match(java.lang.String, java.lang.String)
	 */
	public boolean match(String columnName, String propertyName) {
		if (columnName == null)
			return false;
		String pName = map.get(columnName.toUpperCase());
		if (pName == null)
			return false;
		else {
			return pName.equals(propertyName);
		}
	}
}
