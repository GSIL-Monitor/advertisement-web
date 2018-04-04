package com.yuanshanbao.ms.mapper.admin;

public interface GroupUserMapper {
	public int insertUserAndGroup(String userName, String groupId);
	
	public int deleteUserAndGroup(String userName, String groupId);
	
	public int deleteRecordsByUsername(String userName);
	
	public int deleteRecordsByGroupId(String groupId);
}
