package com.dbutils.dbutilsext;

 
public class EqualsIgnoreCaseMatcher implements Matcher {

	/*
	 * (non-Javadoc)
	 * @see com.yang.commons.dbutils.Matcher#match(java.lang.String, java.lang.String)
	 */
	public boolean match(String columnName, String propertyName) {
		if (columnName == null)
			return false;
		else {
			return columnName.equals(propertyName);
		}
	} 
}
