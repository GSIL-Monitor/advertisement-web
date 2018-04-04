package com.yuanshanbao.ms.mapper.admin;

public interface SysModuleRightMapper {
	public int insertModuleAndRight(String module_id, String right_id);
	
	public int deleteModuleAndRight(String module_id, String right_id);
	
	public int deleteRecordsByModuleId(String module_id);
	
	public int deleteRecordsByRightId(String right_id);
}
