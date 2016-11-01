package com.yogapay.boss.utils.dbutils;

public class EqualsIgnoreCaseMatcher implements Matcher {
	public boolean match(String columnName, String propertyName) {
		if (columnName == null)
			return false;
		else {
			return columnName.equals(propertyName);
		}
	}

}
