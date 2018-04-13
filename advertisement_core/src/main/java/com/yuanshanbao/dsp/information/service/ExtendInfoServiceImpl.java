package com.yuanshanbao.dsp.information.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.CommonUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.activity.service.ActivityService;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.information.dao.ExtendInfoDao;
import com.yuanshanbao.dsp.information.handler.IInformationHandler;
import com.yuanshanbao.dsp.information.model.ExtendInfo;
import com.yuanshanbao.dsp.information.model.FieldConstrains;
import com.yuanshanbao.dsp.information.model.FieldTagsType;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.merchant.service.MerchantService;
import com.yuanshanbao.dsp.page.model.Page;
import com.yuanshanbao.dsp.page.model.PageStep;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.user.service.UserService;

@Service
public class ExtendInfoServiceImpl implements ExtendInfoService {

	private static final String HANDLER_PATH = "com.yuanshanbao.dsp.information.handler.common.";

	@Autowired
	private ExtendInfoDao extendInfoDao;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private InformationService informationService;

	@Autowired
	private UserService userService;

	@Autowired
	private InformationFieldService informationFieldService;

	@Autowired
	private FieldConstrainsService fieldConstantsService;

	@Autowired
	private ExtendInfoService extendInfoService;

	@Override
	public void insertExtendInfo(ExtendInfo extendInfo) {

		int result = -1;

		result = extendInfoDao.insertExtendInfo(extendInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void updateExtendInfo(ExtendInfo extendInfo) {

		int result = -1;

		result = extendInfoDao.updateExtendInfo(extendInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteExtendInfo(ExtendInfo extendInfo) {

		int result = -1;

		result = extendInfoDao.deleteExtendInfo(extendInfo);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<ExtendInfo> selectExtendInfo(ExtendInfo extendInfo, PageBounds pageBounds) {
		return setProperty(extendInfoDao.selectExtendInfo(extendInfo, pageBounds));
	}

	private List<ExtendInfo> setProperty(List<ExtendInfo> extendInfoList) {

		List<Long> informationIds = new ArrayList<Long>();
		List<Long> informationFieldIds = new ArrayList<Long>();

		for (ExtendInfo extendInfo : extendInfoList) {
			informationIds.add(extendInfo.getInformationId());
			informationFieldIds.add(extendInfo.getFieldId());
		}

		Map<Long, Information> informationMap = informationService.selectInformationByIds(informationIds);
		Map<Long, InformationField> fieldMap = informationFieldService.selectInformationFieldByIds(informationFieldIds);
		for (ExtendInfo extendInfo : extendInfoList) {
			extendInfo.setInformation(informationMap.get(extendInfo.getInformationId()));
			extendInfo.setInformationField(fieldMap.get(extendInfo.getFieldId()));
		}

		return extendInfoList;
	}

	@Override
	public Map<String, ExtendInfo> selectExtendInfo(Map<String, Object> params) {

		Map<String, ExtendInfo> map = new HashMap<String, ExtendInfo>();

		if (params == null) {
			return map;
		}

		List<ExtendInfo> extendInfoList = extendInfoDao.selectExtendInfoById(params);

		for (ExtendInfo extendInfo : extendInfoList) {
			map.put(extendInfo.getExtendInfoId() + "", extendInfo);
		}

		return map;
	}

	@Override
	public void insertOrUpdateExtendInfo(ExtendInfo extendInfo) {
		if (extendInfo.getExtendInfoId() == null) {
			insertExtendInfo(extendInfo);
		} else {
			updateExtendInfo(extendInfo);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveExtendInfo(Map<String, String> parameterMap, Information information, Page page) {
		// 获取步骤里面所有的参数
		List<InformationField> stepFields = new ArrayList<InformationField>();
		for (PageStep pageStep : page.getPageStepList()) {
			stepFields.addAll(pageStep.getFieldList());
		}
		if (stepFields == null || stepFields.size() == 0) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}
		// field列表
		List<InformationField> fieldList = new ArrayList<InformationField>();
		getCheckFieldList(parameterMap, information, stepFields, fieldList);
		// 更新或者插入ExtendInfo
		for (InformationField field : fieldList) {
			String value = parameterMap.get(field.getFieldName());
			if (StringUtils.isNotBlank(value)) {
				ExtendInfo extendInfo = new ExtendInfo();
				extendInfo.setUserId(information.getUserId());
				extendInfo.setInformationId(information.getInformationId());
				extendInfo.setFieldId(field.getFieldId());
				extendInfo.setStatus(CommonStatus.ONLINE);
				List<ExtendInfo> extendInfoList = extendInfoService.selectExtendInfo(extendInfo, new PageBounds());
				if (extendInfoList != null && extendInfoList.size() > 0) {
					extendInfo.setExtendInfoId(extendInfoList.get(0).getExtendInfoId());
				}
				extendInfo.setKey(field.getFieldName());
				extendInfo.setName(field.getName());
				extendInfo.setValueAndTags(field, value);
				insertOrUpdateExtendInfo(extendInfo);
			}
		}
	}

	/**
	 * 检测选项列表
	 * 
	 * @param request
	 * @param information
	 * @param stepFields
	 * @param fieldList
	 */
	protected void getCheckFieldList(Map<String, String> parameterMap, Information information,
			List<InformationField> stepFields, List<InformationField> fieldList) {
		// Information里面的参数
		List<String> paramters = CommonUtil.getParameter(information.getClass());
		// Information field列表
		List<InformationField> informationFields = new ArrayList<InformationField>();
		// 无需显示的field列表
		List<InformationField> hiddenFields = new ArrayList<InformationField>();
		// 选中的关联选项的field列表
		List<InformationField> selectFields = new ArrayList<InformationField>();
		for (InformationField field : stepFields) {
			// 去掉Information里面的变量用于存储ExtendInfo
			for (String paramter : paramters) {
				if (field.getFieldName().equals(paramter)) {
					informationFields.add(field);
					break;
				}
			}
			// 去除关联选项中未被选中的选项
			if (field.getRelativeField() != null) {
				for (InformationField informationField : stepFields) {
					String value = parameterMap.get(informationField.getFieldName());
					if (informationField.getFieldId().equals(field.getRelativeField()) && ValidateUtil.isNumber(value)
							&& field.getRelativeValue().equals(value)) {
						selectFields.add(field);
						break;
					}
				}
				hiddenFields.add(field);
			}
			fieldList.add(field);
		}
		hiddenFields.removeAll(selectFields);
		fieldList.removeAll(hiddenFields);
		for (InformationField field : fieldList) {
			// 检测表单
			checkForm(parameterMap, field, information);
		}
		fieldList.removeAll(informationFields);
	}

	/**
	 * 检测表单（判空+反射判断+FieldConstants检测）
	 * 
	 * @param request
	 * @param field
	 * @param information
	 * @return
	 */
	private String checkForm(Map<String, String> parameterMap, InformationField field, Information information) {
		String value = parameterMap.get(field.getFieldName());
		String valueContent = value;
		if (field.required()) {
			if (StringUtils.isBlank(value) || "null".equals(value)) {
				throw new BusinessException(field.getName(), ComRetCode.WRONG_PARAMETER);
			}
		}
		if (field.getTagsType() != null && field.getTagsType().equals(FieldTagsType.HAS_TAGS)
				&& StringUtils.isNotBlank(value)) {
			if (!ValidateUtil.isNumber(value)) {
				throw new BusinessException(field.getName(), ComRetCode.WRONG_PARAMETER);
			}
			Long tagsId = Long.parseLong(value);
			Tags tags = ConstantsManager.getTags(tagsId);
			if (tags == null) {
				throw new BusinessException(field.getName(), ComRetCode.WRONG_PARAMETER);
			}
			valueContent = tags.getTagsId().toString();
		}
		validateInformation(information, field, parameterMap);
		return valueContent;
	}

	/**
	 * 检测Information和FieldConstants（反射判断+FieldConstants检测）
	 * 
	 * @param request
	 * @param field
	 * @param information
	 * @return
	 */
	private void validateInformation(Information information, InformationField field, Map<String, String> parameterMap) {
		if (StringUtils.isBlank(field.getHandler())) {
			field.setHandler(HANDLER_PATH + CommonUtil.upperCase(field.getFieldName()) + "Handler");
		}
		IInformationHandler handler;
		try {
			handler = (IInformationHandler) Class.forName(field.getHandler()).newInstance();
			if (handler != null) {
				handler.handleInformation(information, field);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			List<FieldConstrains> fieldConstants = field.getFieldConstrains();
			if (fieldConstants != null && fieldConstants.size() > 0) {
				fieldConstantsService.checkField(fieldConstants, field, parameterMap);
			}
		}
	}

	@Override
	public Map<String, String> getExtendInfoMap(String userId, Long informationId) {
		Map<String, String> map = new HashMap<String, String>();
		ExtendInfo params = new ExtendInfo();
		params.setUserId(userId);
		params.setInformationId(informationId);
		List<ExtendInfo> extendInfoList = extendInfoDao.selectExtendInfo(params, new PageBounds());

		for (ExtendInfo extendInfo : extendInfoList) {
			Long fieldId = null;
			if (ValidateUtil.isNumber(extendInfo.getKey())) {
				fieldId = Long.parseLong(extendInfo.getKey());
				InformationField field = informationFieldService.selectInformationField(fieldId);
				if (field != null) {
					map.put(field.getFieldName(), extendInfo.getValue());
				}
			}
		}
		return map;
	}

	@Override
	public Map<Long, List<ExtendInfo>> selectExtendInfoByInformationIds(List<Long> informationIds) {
		Map<Long, List<ExtendInfo>> extendInfoList = new LinkedHashMap<Long, List<ExtendInfo>>();
		if (informationIds == null || informationIds.size() == 0) {
			return extendInfoList;
		}
		List<ExtendInfo> infoList = setProperty(extendInfoDao.selectExtendInfoByInformationIds(informationIds));
		for (ExtendInfo extendInfo : infoList) {
			List<ExtendInfo> exist = extendInfoList.get(extendInfo.getInformationId());
			if (exist == null) {
				exist = new ArrayList<ExtendInfo>();
				extendInfoList.put(extendInfo.getInformationId(), exist);
			}
			exist.add(extendInfo);
		}
		return extendInfoList;
	}
}
