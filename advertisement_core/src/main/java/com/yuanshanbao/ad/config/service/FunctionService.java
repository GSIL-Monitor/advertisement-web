package com.yuanshanbao.ad.config.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.config.model.Function;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface FunctionService {

	public void insertFunction(Function function);

	public void updateFunction(Function function);

	public void deleteFunction(Function function);

	public List<Function> selectFunctions(Function function, PageBounds pageBounds);

	public Map<Long, Function> selectFunctionByIds(List<Long> idsList);

	public Function selectFunctionById(Long functionId);

	public Map<String, Function> selectFunctionByKeys(List<String> keys);

	public List<Function> selectFunctionsByCategory(Integer category);

}
