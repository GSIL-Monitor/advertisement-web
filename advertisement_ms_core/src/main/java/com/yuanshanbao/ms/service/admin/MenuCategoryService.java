package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MenuCategoryService {
	public List<MenuCategory> queryMenuCategorys(MenuCategory menuCategory, PageBounds pageBounds);
	
	public boolean deleteMenuCategory(MenuCategory menuCategory);
	
	public boolean isExistMenuCategory(String name);
	
	public boolean insertMenuCategory(MenuCategory menuCategory);
	
	public boolean updateMenuCategory(MenuCategory menuCategory);
}
