package com.yuanshanbao.dsp.config.dao;

import java.util.List;

import com.yuanshanbao.dsp.config.model.Function;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface FunctionDao {

	public int insertFunction(Function function);

	public int updateFunction(Function function);

	public int deleteFunction(Function function);

	public List<Function> selectFunctions(Function function, PageBounds pageBounds);

	public List<Function> selectFunctionByIds(List<Long> idsList);

	public List<Function> selectFunctionByKeys(List<String> keys);
}
