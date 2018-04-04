package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuanshanbao.ms.mapper.admin.MenuCategoryMapper;
import com.yuanshanbao.ms.mapper.admin.MenuMapper;
import com.yuanshanbao.ms.model.admin.MenuCategory;
import com.yuanshanbao.ms.service.admin.MenuCategoryService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class MenuCategoryServiceImpl implements MenuCategoryService {
	
	@Autowired
	private MenuCategoryMapper mcMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<MenuCategory> queryMenuCategorys(
			MenuCategory menuCategory, PageBounds pageBounds) {
		return mcMapper.queryMenuCategorys(menuCategory, pageBounds);
	}

	@Override
	public boolean deleteMenuCategory(MenuCategory menuCategory) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = transactionManager.getTransaction(def);
		
		result = mcMapper.deleteMenuCategory(menuCategory);
		result = menuMapper.deleteMenuByCategoryId(menuCategory.getId());
		transactionManager.commit(status);
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isExistMenuCategory(String name) {
		List<MenuCategory> mcs = mcMapper.queryMenuCategoryByName(name);
		
		return mcs != null && mcs.size() > 0;
	}

	@Override
	public boolean insertMenuCategory(MenuCategory menuCategory) {
		int result = -1;
		
		result = mcMapper.insertMenuCategory(menuCategory);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateMenuCategory(MenuCategory menuCategory) {
		int result = -1;
		
		result = mcMapper.updateMenuCategory(menuCategory);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
}
