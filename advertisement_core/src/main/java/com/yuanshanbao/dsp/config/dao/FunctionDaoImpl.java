package com.yuanshanbao.dsp.config.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl implements FunctionDao {

	@Override
	public int insertFunction(Function function) {
		return getSqlSession().insert("function.insertFunction", function);
	}

	@Override
	public int updateFunction(Function function) {
		return getSqlSession().update("function.updateFunction", function);
	}

	@Override
	public int deleteFunction(Function function) {
		return getSqlSession().delete("function.deleteFunction", function);
	}

	@Override
	public List<Function> selectFunctions(Function function, PageBounds pageBounds) {
		return getSqlSession().selectList("function.selectFunctions", function, pageBounds);
	}

	@Override
	public List<Function> selectFunctionByIds(List<Long> idsList) {
		if (idsList == null || idsList.size() == 0) {
			return new ArrayList<Function>();
		}
		return getSqlSession().selectList("function.selectFunctionByIds", idsList);
	}

	@Override
	public List<Function> selectFunctionByKeys(List<String> keys) {
		if (keys == null || keys.size() == 0) {
			return new ArrayList<Function>();
		}
		return getSqlSession().selectList("function.selectFunctionByKeys", keys);
	}

}
