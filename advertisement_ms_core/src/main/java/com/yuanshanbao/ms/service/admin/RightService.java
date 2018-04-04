package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface RightService {
	public List<Right> queryRightsByGroupId(String flag, String groupid);
	
	public List<Right> queryRightsByModuleId(String flag, String moduleId, PageBounds pageBounds);

	public List<Right> queryRights(Right right, PageBounds pageBounds);
	
	public List<Right> queryAvailableMenuRights();
	
	public List<Right> queryRightsByUsername(String userName);

	public boolean isExistRight(String name, String url);
	
	public boolean insertRight(Right right);
	
	public boolean deleteRight(Right right);
}
