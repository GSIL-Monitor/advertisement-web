package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MenuCategoryMapper {
	public List<MenuCategory> queryMenuCategorys(MenuCategory menuCategory, PageBounds pageBounds);
	
	public List<MenuCategory> queryMenuCategoryByName(String name);
	
	public int deleteMenuCategory(MenuCategory menuCategory);
	
	public int insertMenuCategory(MenuCategory menuCategory);
	
	public int updateMenuCategory(MenuCategory menuCategory);
}
