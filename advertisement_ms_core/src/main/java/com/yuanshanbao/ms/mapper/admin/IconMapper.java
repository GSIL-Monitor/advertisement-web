package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yuanshanbao.ms.model.admin.Icon;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface IconMapper {
	public List<Icon> queryIcons(Icon icon, PageBounds pageBounds);
	
	public List<String> queryIconNames();
	
	public int insertIcon(@Param("name") String name, @Param("image") byte[] content);
	
	public int deleteIcon(String name);
}
