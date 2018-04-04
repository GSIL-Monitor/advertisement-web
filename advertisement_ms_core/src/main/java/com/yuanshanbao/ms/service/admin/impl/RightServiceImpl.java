package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuanshanbao.ms.mapper.admin.GroupMapper;
import com.yuanshanbao.ms.mapper.admin.RightMapper;
import com.yuanshanbao.ms.mapper.admin.SysModuleRightMapper;
import com.yuanshanbao.ms.model.admin.Right;
import com.yuanshanbao.ms.service.admin.RightService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class RightServiceImpl implements RightService {

	@Autowired
	private RightMapper rightMapper;
	
	@Autowired
	private SysModuleRightMapper smMapper;

	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<Right> queryRightsByGroupId(String flag, String groupid) {
		return rightMapper.queryRightsByGroupId(flag, groupid);
	}
	
	@Override
	public List<Right> queryRightsByModuleId(String flag, String moduleId, PageBounds pageBounds) {
		return rightMapper.queryRightsByModuleId(flag, moduleId, pageBounds);
	}

	@Override
	public List<Right> queryRights(Right right, PageBounds pageBounds) {
		List<Right> rightList = rightMapper.queryRights(right, pageBounds);
		
		// TODO
//		if (rightList != null && rightList.size() > 0) {
//			for (Right r : rightList) {
//				List<Group> group = groupMapper.queryGroupsByRid(r.getId());
//				right.setGroups(group);
//			}
//		}
		
		return rightList;
	}
	
	public List<Right> queryAvailableMenuRights() {
		return rightMapper.queryAvailableMenuRights();
	}

	@Override
	public boolean isExistRight(String name, String url) {
		List<Right> rights = rightMapper.queryRightByNameOrUrl(name, url);
		
		return rights != null && rights.size() > 0;
	}
	
	@Override
	public boolean insertRight(Right right) {
		int result = -1;
		
		result = rightMapper.insertRight(right);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteRight(Right right) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

		TransactionStatus status = transactionManager.getTransaction(def);
		
		result = rightMapper.deleteRight(right);
		result = smMapper.deleteRecordsByRightId(right.getId());
		
		transactionManager.commit(status);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Right> queryRightsByUsername(String userName) {
		return rightMapper.queryRightsByUsername(userName);
	}
}