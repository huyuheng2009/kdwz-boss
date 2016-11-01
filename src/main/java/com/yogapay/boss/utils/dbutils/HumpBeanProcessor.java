package com.yogapay.boss.utils.dbutils;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.dbutils.BeanProcessor;

public class HumpBeanProcessor extends BeanProcessor {

	private Matcher matcher;

	public HumpBeanProcessor() {
		matcher = new EqualsIgnoreCaseMatcher();
	}

	public HumpBeanProcessor(Matcher matcher) {
		this.matcher = matcher;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	protected int[] mapColumnsToProperties(ResultSetMetaData rsmd,
			PropertyDescriptor[] props) throws SQLException {
		if (matcher == null)
			throw new IllegalStateException("Matcher must be setted!");

		int cols = rsmd.getColumnCount();
		int columnToProperty[] = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);
			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}
			for (int i = 0; i < props.length; i++) {
				if (matcher.match(columnName, props[i].getName())) {
					columnToProperty[col] = i;
					break;
				}
			}
		}

		return columnToProperty;
	}
}
