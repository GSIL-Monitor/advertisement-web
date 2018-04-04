package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MenuMapper {
	public int insertMenu(Menu menu);
	
	public int deleteMenu(Menu menu);
	
	public int deleteMenuByCategoryId(String catetoryId);
	
	public int updateMenuIcon(@Param("id") String id, @Param("icon_name") String icon_name);

	public List<Menu> queryMenus(Menu menu, PageBounds pageBounds);
	
	public Menu queryLastSbling(String idStart);
	
	public List<Menu> queryMenusByUsername(@Param("userName") String userName, @Param("categoryId") String categoryId);
}
