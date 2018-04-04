package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yuanshanbao.ms.model.admin.Group;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface GroupMapper {
	public List<Group> queryGroups(Group group, PageBounds pageBounds);
	
	public List<Group> queryGroupByName(String name);
	
	public Group queryLastSbling(String idStart);
	
	public int insertGroup(Group group);
	
	public int deleteGroup(Group group);
	
	public List<Group> queryGroupsByUsername(@Param("flag") String flag, @Param("userName") String userName, PageBounds pageBounds);
}
