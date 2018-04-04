package com.yuanshanbao.ms.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import com.yuanshanbao.ms.mapper.admin.SysModuleMapper;
import com.yuanshanbao.ms.mapper.admin.SysModuleRightMapper;
import com.yuanshanbao.ms.model.admin.SysModule;
import com.yuanshanbao.ms.service.admin.SysModuleService;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class SysModuleServiceImpl implements SysModuleService {
	
	@Autowired
	private SysModuleMapper sysModuleMapper;
	
	@Autowired
	private SysModuleRightMapper sysMrMapper;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Override
	public List<SysModule> queryModules(SysModule module, PageBounds pageBounds) {
		return sysModuleMapper.querySysModules(module, pageBounds);
	}
	
	public List<SysModule> queryModulesByGroupId(String groupId, String flag, PageBounds pageBounds) {
		return sysModuleMapper.queryModulesByGroupId(flag, groupId, pageBounds);
	}

	@Override
	public boolean isExistModule(String name) {
		List<SysModule> modules = sysModuleMapper.querySysModuleByName(name);
		
		return modules != null && modules.size() > 0;
	}
	
	@Override
	public boolean insertModule(SysModule module) {
		int result = -1;
		
		result = sysModuleMapper.insertSysModule(module);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateModule(SysModule module) {
		int result = -1;
		
		result = sysModuleMapper.updateSysModule(module);
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deleteModule(SysModule module) {
		int result = -1;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		result = sysModuleMapper.deleteSysModule(module);
		result = sysMrMapper.deleteRecordsByModuleId(module.getId());
		transactionManager.commit(status);
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertModuleRight(String moduleId, String rightId) {
		int result = -1;
		if (StringUtils.hasText(rightId) && StringUtils.hasText(moduleId)) {
			result = sysMrMapper.insertModuleAndRight(moduleId, rightId);
		}
		
		result = 0;
		
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean insertModuleRights(String moduleId, String[] rightIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		if (rightIds != null) {
			for (String rightId : rightIds) {
				if (StringUtils.hasText(rightId) && StringUtils.hasText(moduleId)) {
					result = sysMrMapper.insertModuleAndRight(moduleId, rightId);
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
	public boolean deleteModuleRight(String moduleId, String rightId) {
		int result = -1;
		if (StringUtils.hasText(rightId) && StringUtils.hasText(moduleId)) {
			result = sysMrMapper.deleteModuleAndRight(moduleId, rightId);
		}
		result = 0;
		if (result >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteModuleRights(String moduleId, String[] rightIds) {
		int result = -1;
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		if (rightIds != null) {
			for (String rightId : rightIds) {
				if (StringUtils.hasText(rightId) && StringUtils.hasText(moduleId)) {
					result = sysMrMapper.deleteModuleAndRight(moduleId, rightId);
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
}
