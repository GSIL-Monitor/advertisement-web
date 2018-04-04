package com.yuanshanbao.ms.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface RightMapper {
	public List<Right> queryRights(Right right, PageBounds pageBounds);
	
	public List<Right> queryRightByNameOrUrl(@Param("name") String name, @Param("url") String url);
	
	public List<Right> queryAvailableMenuRights();
	
	public int insertRight(Right right);
	
	public int deleteRight(Right right);

	public int insertGroupRight(Map<String, String> paramMap);
	
	public int deleteGroupRight(Map<String, String> paramMap);
	
	/**
	 * Query rights which is in or not in module.
	 * 
	 * @param flag true->in|false->not in
	 * @param moduleId
	 * @param pageBounds 
	 * @return
	 */
	public List<Right> queryRightsByModuleId(@Param("flag") String flag, @Param("moduleId") String moduleId, PageBounds pageBounds);
	
	public List<Right> queryRightsByGroupId(@Param("flag") String flag, @Param("groupId") String groupId);
	
	public List<Right> queryRightsByUsername(String userName);
}
