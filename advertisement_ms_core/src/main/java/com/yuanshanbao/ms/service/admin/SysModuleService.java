package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface SysModuleService {
	public List<SysModule> queryModules(SysModule module, PageBounds pageBounds);
	
	public List<SysModule> queryModulesByGroupId(String groupId, String flag, PageBounds pageBounds);
	
	public boolean isExistModule(String name);
	
	public boolean insertModule(SysModule module);
	
	public boolean updateModule(SysModule module);
	
	public boolean deleteModule(SysModule module);
	
	public boolean insertModuleRight(String moduleId, String rightId);
	
	public boolean insertModuleRights(String moduleId, String[] rightIds);
	
	public boolean deleteModuleRight(String moduleId, String rightId);
	
	public boolean deleteModuleRights(String moduleId, String[] rightIds);
}
