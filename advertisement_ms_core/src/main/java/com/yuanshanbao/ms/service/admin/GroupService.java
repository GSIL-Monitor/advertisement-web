package com.yuanshanbao.ms.service.admin;

import java.util.List;

import com.yuanshanbao.ms.model.admin.Group;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface GroupService {
	public List<Group> queryGroups(Group group, PageBounds pageBounds);
	
	public String queryLastSblingId(String idStart);
	
	public String getNextGroupId(String lastGroupId);
	
	public boolean isExistGroup(String name);

	public boolean insertGroup(Group group);
	
	public boolean deleteGroup(Group group);
	
	public boolean insertGroupModules(String groupId, String[] moduleIds);
	
	public boolean deleteGroupModules(String groupId, String[] moduleIds);
	
	public boolean insertUserGroup(String userName, String groupId);
	
	public boolean insertUserGroups(String userName, String[] groupIds);

	public boolean deleteUserGroup(String userName, String groupId);
	
	public boolean deleteUserGroups(String userName, String[] groupIds);
	
	public List<Group> queryGroupsByUsername(String flag, String username, PageBounds pageBounds);
}
