package com.yuanshanbao.ad.base.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.yuanshanbao.paginator.domain.PageList;
import com.yuanshanbao.paginator.domain.Paginator;

@Component("baseDaoImpl")
public class BaseDaoImpl extends DaoSupport {

	private SqlSession sqlSession;

	private boolean externalSqlSession;

	@Autowired
	@Qualifier("sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		if (!(this.externalSqlSession))
			this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSession = sqlSessionTemplate;
		this.externalSqlSession = true;
	}

	public SqlSession getSqlSession() {
		return this.sqlSession;
	}

	protected void checkDaoConfig() {
		Assert.notNull(this.sqlSession, "Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PageList selectPaginationList(String statement, Object parameter, Paginator paginator) {
		PageList paginationList = new PageList();

		if (parameter == null) {
			throw new RuntimeException("parameter can not be null");
		}
		if (parameter instanceof Map<?, ?>) {
			((Map) parameter).put("paginationInfo", paginator);
		}
		List result = this.getSqlSession().selectList(statement, parameter);

		paginationList.addAll(result);
		if (paginator == null) {
			paginator = new Paginator(1, result.size(), result.size());
		}
		paginationList.setPaginator(paginator);;

		return paginationList;
	}

}
