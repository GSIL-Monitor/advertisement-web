package com.yuanshanbao.ms.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface SysModuleMapper {
	public List<SysModule> querySysModules(SysModule module, PageBounds pageBounds);
	
	public List<SysModule> querySysModuleByName(String name);
	
	public int insertSysModule(SysModule module);
	
	public int updateSysModule(SysModule module);
	
	public int deleteSysModule(SysModule module);
	
	public List<SysModule> queryModulesByGroupId(@Param("flag") String flag, @Param("groupId") String groupId, PageBounds pageBounds);
}
