package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.yuanshanbao.ms.mapper.admin.GroupMapper;
import com.yuanshanbao.ms.mapper.admin.MenuMapper;
import com.yuanshanbao.ms.model.admin.Menu;
import com.yuanshanbao.ms.service.admin.MenuService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private GroupMapper groupMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<Menu> queryMenus(Menu menu, PageBounds pageBounds) {
		return menuMapper.queryMenus(menu, pageBounds);
	}
	
	@Override
	public String queryLastSblingId(String idStart) {
		Menu menu = menuMapper.queryLastSbling(idStart);
		if(menu == null || menu.getId() == null) {
			return idStart + "000";
		} else {
			return menu.getId();
		}
	}
	
	@Override
	public String getNextMenuId(String lastMenuId) {
		String sblingNum = lastMenuId.substring(lastMenuId.length() - 3);
		
		int currentNum = 0;
		
		for (int i = 0; i < sblingNum.length(); i++) {
			String c = String.valueOf(sblingNum.charAt(i));
			currentNum = (int) (currentNum + Integer.valueOf(c)*100/Math.pow(10.0, Double.valueOf(i)));
		}
		
		int nextNum = currentNum + 1;
		
		String nextNumStr = String.valueOf(nextNum);
		
		if (nextNumStr.length() < 3) {
			if (nextNumStr.length() == 2) {
				nextNumStr = "0" + nextNumStr;
			}
			
			if (nextNumStr.length() == 1) {
				nextNumStr = "00" + nextNumStr;
			}
		}
		
		String id = lastMenuId.substring(0, lastMenuId.length() - 3) + nextNumStr;
		
		return id;
	}

	@Override
	public boolean insertMenu(Menu menu) {
		int result = -1;
		
		result = menuMapper.insertMenu(menu);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteMenu(Menu menu) {
		int result = -1;
		
		result = menuMapper.deleteMenu(menu);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Menu> queryMenusByUsername(String userName, String categoryId) {
		return menuMapper.queryMenusByUsername(userName, categoryId);
	}
	
	@Override
	public boolean updateMenuIcon(String id, String icon_name) {
		int result = -1;
		
		result = menuMapper.updateMenuIcon(id, icon_name);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
}
