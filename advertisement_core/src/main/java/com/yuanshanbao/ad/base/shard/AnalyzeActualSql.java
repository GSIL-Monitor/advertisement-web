package com.yuanshanbao.ad.base.shard;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.PropertyUtil;
import com.yuanshanbao.common.util.ReflectHelperUtil;
import com.yuanshanbao.ad.common.constant.CommonConstant;

public class AnalyzeActualSql {

	private MappedStatement mappedStatement;
	private Object parameterObject;
	private BoundSql boundSql;

	public AnalyzeActualSql(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
		this.setMappedStatement(mappedStatement);
		this.setParameterObject(parameterObject);
		this.setBoundSql(boundSql);
	}

	public String getActualSql(String originalSql, TableShard tableShard) throws Exception {
		String newSql = null;
		originalSql = originalSql.toLowerCase();

		if (tableShard == null) {
			return newSql;
		}

		String tableName = tableShard != null ? tableShard.tableName().trim() : "";
		String shardType = PropertyUtil.getProperty(tableShard != null ? tableShard.shardType().trim() : "");

		if (!shardType.startsWith(CommonConstant.PERCENT_SPLIT_STR)) {
			return newSql;
		}

		Long value = getShardValue(tableShard);
		if (value == null) {
			return originalSql;
		}
		long div = Long.parseLong(shardType.substring(1));
		long suffix = value % div;
		String newTableName = tableName + CommonConstant.COMMON_SPLIT_STR + suffix;
		if (!originalSql.contains(newTableName)) {
			newSql = originalSql.replaceAll(tableName, newTableName);
		}
		return newSql;
	}

	private Long getShardValue(TableShard tableShard) throws NoSuchFieldException, IllegalAccessException {
		if (tableShard == null) {
			return null;
		}
		String[] shard = tableShard.shardBy();
		Long value = null;
		for (String shardBy : shard) {
			switch (shardBy) {
			case CommonConstant.USER_SHARD_BY_ID:
			case CommonConstant.USER_SHARD_BY_MOBILE:
			case CommonConstant.INDEX_USER_SHARD_BY:
				String shardByStr = getShardByStrValue(shardBy);
				try {
					if (StringUtils.isNotBlank(shardByStr)) {
						value = Long.valueOf(CommonConstant.getShardSuffixByUserId(shardByStr));
					}
				} catch (Exception e) {
					LoggerUtil.error("[getActualSql]", e);
				}
				break;
			}
		}
		return value;
	}

	public static String generateTableNameByPeriod(TableShard tableShard, String periodId) {
		String tableName = tableShard != null ? tableShard.tableName().trim() : "";
		String shardType = PropertyUtil.getProperty(tableShard != null ? tableShard.shardType().trim() : "");
		long div = Long.parseLong(shardType.substring(1));
		long value = Long.parseLong(periodId);
		long suffix = value % div;
		String newTableName = tableName + CommonConstant.COMMON_SPLIT_STR + suffix;
		return newTableName;
	}

	private String getShardByStrValue(String shardBy) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Object parameterObject = boundSql.getParameterObject();
		String value = null;
		if (parameterObject != null) {
			if (parameterObject instanceof String) {
				value = (String) parameterObject;
			} else if (parameterObject instanceof Map<?, ?>) {
				value = (String) ((Map<?, ?>) parameterObject).get(shardBy);
			} else {
				Field valueField = ReflectHelperUtil.getFieldByFieldName(parameterObject, shardBy);
				if (valueField != null) {
					Object vObject = ReflectHelperUtil.getValueByFieldName(parameterObject, shardBy);
					if (vObject!=null) {
						value = String.valueOf(vObject);
					}
				}
			}
		}
		return value;
	}

	private Long getShardByLongValue(String shardBy) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Object parameterObject = boundSql.getParameterObject();
		Long value = null;
		if (parameterObject != null) {
			if (parameterObject instanceof Long) {
				value = (Long) parameterObject;
			} else if (parameterObject instanceof Map<?, ?>) {
				value = (Long) ((Map<?, ?>) parameterObject).get(shardBy);
			} else {
				Field valueField = ReflectHelperUtil.getFieldByFieldName(parameterObject, shardBy);
				if (valueField != null) {
					value = (Long) ReflectHelperUtil.getValueByFieldName(parameterObject, shardBy);
				}
			}
		}
		return value;
	}

	/**
	 * 取得shardBy字段的值 注意：根据多个字段进行分库分表时，必须固定各个字段的顺序，这里的返回的取值也是按着shardBy本身的顺序
	 * 这里按String类型
	 */
	private String[] getShardByValues(String shardBy) throws Exception {
		String[] shardByArr = shardBy.split(",");
		String[] valueArr = new String[shardByArr.length];
		Object parameterObject = boundSql.getParameterObject();
		if (parameterObject != null) {
			for (int i = 0; i < shardByArr.length; i++) {
				String eachShardBy = shardByArr[i];
				if (parameterObject instanceof Map<?, ?>) {
					valueArr[i] = (String) ((Map<?, ?>) parameterObject).get(eachShardBy);
				} else {
					Field field = ReflectHelperUtil.getFieldByFieldName(parameterObject, eachShardBy);
					if (field != null) {
						valueArr[i] = (String) ReflectHelperUtil.getValueByFieldName(parameterObject, eachShardBy);
					}
				}
			}
		}
		return valueArr;
	}

	public Object getParameterObject() {
		return parameterObject;
	}

	public void setParameterObject(Object parameterObject) {
		this.parameterObject = parameterObject;
	}

	public MappedStatement getMappedStatement() {
		return mappedStatement;
	}

	public void setMappedStatement(MappedStatement mappedStatement) {
		this.mappedStatement = mappedStatement;
	}

	public BoundSql getBoundSql() {
		return boundSql;
	}

	public void setBoundSql(BoundSql boundSql) {
		this.boundSql = boundSql;
	}
}
