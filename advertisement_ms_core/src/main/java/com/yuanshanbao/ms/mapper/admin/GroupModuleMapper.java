package com.yuanshanbao.ms.mapper.admin;

public interface GroupModuleMapper {
	public int insertGroupAndModule(String groupId, String moduleId);
	
	public int deleteGroupAndModule(String groupId, String moduleId);
	
	public int deleteRecordsByGroupId(String groupId);
	
	public int deleteRecordsByModuleId(String moduleId);
}
