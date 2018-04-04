package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MenuService {
	public List<Menu> queryMenus(Menu menu, PageBounds pageBounds);
	
	public String queryLastSblingId(String idStart);
	
	public String getNextMenuId(String lastMenuId);

	public boolean insertMenu(Menu menu);
	
	public boolean deleteMenu(Menu menu);
	
	public boolean updateMenuIcon(String id, String icon_name);
	
	public List<Menu> queryMenusByUsername(String userName, String categoryId);
}
