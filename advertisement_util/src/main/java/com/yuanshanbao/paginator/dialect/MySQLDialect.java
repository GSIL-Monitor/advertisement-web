package com.yuanshanbao.paginator.dialect;

/**
 * @author badqiu
 */
public class MySQLDialect extends Dialect {

	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
		if (offset > 0) {
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		} else {
			return sql + " limit " + limitPlaceholder;
		}
	}

	public String getCountString(String sql) {
		if (sql.toLowerCase().contains("union")) {
			return "select count(1) from (" + sql + ") count";
		}
		String resultSql = "select count(1) " + sql.substring(sql.toLowerCase().indexOf("from"), sql.length());
		if (resultSql.toLowerCase().lastIndexOf("order by") != -1) {
			resultSql = resultSql.substring(0, resultSql.toLowerCase().lastIndexOf("order by"));
		}
		return resultSql;
	}
}
