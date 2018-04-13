package com.yuanshanbao.dsp.information.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.information.dao.InformationFieldDao;
import com.yuanshanbao.dsp.information.model.InformationField;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

@Service
public class InformationFieldServiceImpl implements InformationFieldService {

	@Autowired
	private InformationFieldDao informationFieldDao;

	@Override
	public List<InformationField> selectInformationFields(InformationField informationField, PageBounds pageBounds) {
		return setProperty(informationFieldDao.selectInformationFields(informationField, pageBounds));
	}

	private List<InformationField> setProperty(List<InformationField> list) {
		List<Long> fieldIds = new ArrayList<Long>();
		for (InformationField informationField : list) {
			fieldIds.add(informationField.getRelativeField());
		}

		Map<Long, InformationField> map = selectInformationFieldByIds(fieldIds);
		for (InformationField informationField : list) {
			informationField.setReferenceField(map.get(informationField.getRelativeField()));
			setValueTags(informationField);
		}
		return list;
	}

	private void setValueTags(InformationField informationField) {
		try {
			String values = informationField.getValues();
			if (StringUtils.isNotBlank(values)) {
				String[] tagsIds = values.split(",");
				if (tagsIds != null && tagsIds.length > 0) {
					List<TagsVo> tagsList = new ArrayList<TagsVo>();
					for (String param : tagsIds) {
						Tags tags = ConstantsManager.getTags(Long.valueOf(param));
						if (tags != null) {
							tagsList.add(new TagsVo(tags));
						}
					}
					informationField.setValueTags(tagsList);
				}
			}
		} catch (Exception e) {
			LoggerUtil.error("[set_property]", e);
		}
	}

	@Override
	public void insertInformationField(InformationField informationField) {
		int result = -1;

		result = informationFieldDao.insertInformationField(informationField);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteInformationField(Long fieldId) {
		int result = -1;

		result = informationFieldDao.deleteInformationField(fieldId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInformationField(InformationField information) {
		int result = -1;

		result = informationFieldDao.updateInformationField(information);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param information
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateInformationField(InformationField informationField) {
		if (informationField.getFieldId() != null) {
			return;
		}
		InformationField exist = selectInformationField(informationField.getFieldId());
		if (exist == null) {
			informationField.setStatus(CommonStatus.ONLINE);
			insertInformationField(informationField);
		} else {
			updateInformationField(informationField);
		}
	}

	@Override
	public Map<Long, InformationField> selectInformationFieldByIds(List<Long> informationIdList) {
		Map<Long, InformationField> map = new HashMap<Long, InformationField>();
		if (informationIdList == null || informationIdList.size() == 0) {
			return map;
		}
		List<InformationField> list = setProperty(informationFieldDao.selectInformationFields(informationIdList));
		for (InformationField informationField : list) {
			map.put(informationField.getFieldId(), informationField);
		}
		return map;
	}

	@Override
	public InformationField selectInformationField(Long fieldId) {
		InformationField informationField = new InformationField();
		informationField.setFieldId(fieldId);
		List<InformationField> list = selectInformationFields(informationField, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
