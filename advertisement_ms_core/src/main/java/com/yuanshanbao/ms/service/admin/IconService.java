package com.yuanshanbao.ms.service.admin;

import java.sql.Blob;
import java.util.List;

import com.yuanshanbao.ms.model.admin.Icon;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface IconService {
	public Blob queryIconByName(String name);
	
	public List<String> queryIconNames();
	
	public List<Icon> queryIcons(String iconName, PageBounds pageBounds);
	
	public boolean insertIcon(String name, byte[] image);
	
	public boolean deleteIcon(String name);
}
