package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.yuanshanbao.ms.mapper.admin.GroupMapper;
import com.yuanshanbao.ms.mapper.admin.GroupModuleMapper;
import com.yuanshanbao.ms.mapper.admin.GroupUserMapper;
import com.yuanshanbao.ms.mapper.admin.MenuMapper;
import com.yuanshanbao.ms.mapper.admin.RightMapper;
import com.yuanshanbao.ms.model.admin.Group;
import com.yuanshanbao.ms.service.admin.GroupService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private GroupUserMapper guMapper;

	@Autowired
	private RightMapper rightMapper;

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private GroupModuleMapper gmMapper;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<Group> queryGroups(Group group, PageBounds pageBounds) {
		List<Group> groupList = groupMapper.queryGroups(group, pageBounds);

		return groupList;
	}
	
	@Override
	public String queryLastSblingId(String idStart) {
		Group group = groupMapper.queryLastSbling(idStart);
		if(group == null || group.getId() == null) {
			return idStart + "000";
		} else {
			return group.getId();
		}
	}
	
	@Override
	public String getNextGroupId(String lastGroupId) {
		String sblingNum = lastGroupId.substring(lastGroupId.length() - 3);
		
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
		
		String id = lastGroupId.substring(0, lastGroupId.length() - 3) + nextNumStr;
		
		return id;
	}
	
	@Override
	public boolean isExistGroup(String name) {
		List<Group> groups = groupMapper.queryGroupByName(name);
		
		return groups != null && groups.size() > 0;
	}
	
	@Override
	public boolean insertGroup(Group group) {
		int result = -1;
		
		result = groupMapper.insertGroup(group);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteGroup(Group group) {
		int result = -1;
		
		result = groupMapper.deleteGroup(group);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertGroupModules(String groupId, String[] moduleIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		if (moduleIds != null) {
			for (String moduleId : moduleIds) {
				if (StringUtils.hasText(groupId) && StringUtils.hasText(moduleId)) {
					result = gmMapper.insertGroupAndModule(groupId, moduleId);
				}
			}
			result = 0;
		} else {
			result = 0;
		}
		transactionManager.commit(status);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteGroupModules(String groupId, String[] moduleIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		if (moduleIds != null) {
			for (String moduleId : moduleIds) {
				if (StringUtils.hasText(groupId) && StringUtils.hasText(moduleId)) {
					result = gmMapper.deleteGroupAndModule(groupId, moduleId);
				}
			}
			result = 0;
		} else {
			result = 0;
		}
		transactionManager.commit(status);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertUserGroup(String userName, String groupId) {
		int result = -1;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		result = guMapper.deleteUserAndGroup(userName, groupId);
		result = guMapper.insertUserAndGroup(userName, groupId);
		transactionManager.commit(status);
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean insertUserGroups(String userName, String[] groupIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		if (userName != null) {
			for (String groupId : groupIds) {
				if (StringUtils.hasText(groupId) && StringUtils.hasText(userName)) {
					result = guMapper.deleteUserAndGroup(userName, groupId);
					result = guMapper.insertUserAndGroup(userName, groupId);
				}
			}
			result = 0;
		} else {
			result = 0;
		}
		transactionManager.commit(status);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteUserGroup(String userName, String groupId) {
		int result = -1;
		
		result = guMapper.deleteUserAndGroup(userName, groupId);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteUserGroups(String userName, String[] groupIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		if (userName != null) {
			for (String groupId : groupIds) {
				if (StringUtils.hasText(groupId) && StringUtils.hasText(userName)) {
					result = guMapper.deleteUserAndGroup(userName, groupId);
				}
			}
			result = 0;
		} else {
			result = 0;
		}
		
		transactionManager.commit(status);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Group> queryGroupsByUsername(String flag, String username, PageBounds pageBounds) {
		return groupMapper.queryGroupsByUsername(flag, username, pageBounds);
	}
}
