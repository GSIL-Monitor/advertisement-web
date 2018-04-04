package com.yuanshanbao.ad.activity.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.activity.dao.ActivityStepDao;
import com.yuanshanbao.ad.activity.model.ActivityStep;
import com.yuanshanbao.ad.information.model.InformationField;
import com.yuanshanbao.ad.information.service.InformationFieldService;
import com.yuanshanbao.ad.user.service.UserService;

@Service
public class ActivityStepServiceImpl implements ActivityStepService {

	@Autowired
	private ActivityStepDao activityStepDao;

	@Autowired
	private UserService userService;

	@Autowired
	private InformationFieldService informationFieldService;

	@Override
	public List<ActivityStep> selectActivitySteps(ActivityStep activityStep,
			PageBounds pageBounds) {
		return setProperty(activityStepDao.selectActivitySteps(activityStep,
				pageBounds));
	}

	private List<ActivityStep> setProperty(List<ActivityStep> list) {
		List<Long> fieldIds = new ArrayList<Long>();
		for (ActivityStep activityStep : list) {
			List<Long> fields = activityStep.getFieldIds();
			if (fields != null) {
				fieldIds.addAll(fields);
			}
		}

		Map<Long, InformationField> map = informationFieldService
				.selectInformationFieldByIds(fieldIds);
		for (ActivityStep activityStep : list) {
			List<InformationField> informationFields = new ArrayList<InformationField>();
			if (activityStep.getFieldIds() != null
					&& activityStep.getFieldIds().size() > 0) {
				for (Long fieldId : activityStep.getFieldIds()) {
					informationFields.add(map.get(fieldId));
				}
			}
			activityStep.setInformationFieldList(informationFields);
		}
		return list;
	}

	@Override
	public ActivityStep selectActivityStep(Long activityStepId) {
		ActivityStep activityStep = new ActivityStep();
		if (activityStepId == null) {
			return null;
		}
		activityStep.setActivityStepId(activityStepId);
		List<ActivityStep> list = selectActivitySteps(activityStep,
				new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertActivityStep(ActivityStep activityStep) {
		int result = -1;

		result = activityStepDao.insertActivityStep(activityStep);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteActivityStep(Long activityStepId) {
		int result = -1;

		result = activityStepDao.deleteActivityStep(activityStepId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateActivityStep(ActivityStep activityStep) {
		int result = -1;

		result = activityStepDao.updateActivityStep(activityStep);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param activityStep
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateActivityStep(ActivityStep activityStep) {
		if (activityStep.getActivityStepId() == null) {
			insertActivityStep(activityStep);
		} else {
			updateActivityStep(activityStep);
		}
	}

	@Override
	public Map<Long, ActivityStep> selectActivitySteps(
			List<Long> activityStepIdList) {
		Map<Long, ActivityStep> map = new HashMap<Long, ActivityStep>();
		if (activityStepIdList == null || activityStepIdList.size() == 0) {
			return map;
		}
		List<ActivityStep> list = setProperty(activityStepDao
				.selectActivitySteps(activityStepIdList));
		for (ActivityStep activityStep : list) {
			map.put(activityStep.getActivityStepId(), activityStep);
		}
		return map;
	}

}
