package com.yuanshanbao.ad.base.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.ad.base.shard.AnalyzeActualSql;
import com.yuanshanbao.ad.base.shard.TableShard;

@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class TableShardInterceptor implements Interceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
	private static final String PATH_PREFIX = "com.yuanshanbao.ad.";

	private String gatherSqlSuffix = "ForCollect";

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
					DEFAULT_REFLECTOR_FACTORY);
		}
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
					DEFAULT_REFLECTOR_FACTORY);
		}
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
		Object parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");

		if (originalSql != null && !originalSql.equals("")) {
			MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate"
					+ ".mappedStatement");
			String id = mappedStatement.getId();

			try {
				if (!id.endsWith(gatherSqlSuffix)) {
					String[] segs = mappedStatement.getResource().split("/");
					int start = segs[2].lastIndexOf("/");
					int end = segs[2].lastIndexOf(".");
					String className = segs[2].substring(start + 1, end - 4);
					className = PATH_PREFIX + segs[1] + ".dao." + className;
					Class<?> classObj = Class.forName(className);
					// 根据配置自动生成分表SQL
					TableShard tableShard = classObj.getAnnotation(TableShard.class);
					if (tableShard != null) {
						AnalyzeActualSql as = new AnalyzeActualSql(mappedStatement, parameterObject, boundSql);
						String newSql = as.getActualSql(originalSql, tableShard);
						if (newSql != null) {
							metaStatementHandler.setValue("delegate.boundSql.sql", newSql);
						}
					} else {
						// System.out.println("11111111111111111111111111");
					}
				}
			} catch (Exception e) {
				LoggerUtil.error("change sql error", e);
			}

		}
		// 传递给下一个拦截器处理
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}
}
