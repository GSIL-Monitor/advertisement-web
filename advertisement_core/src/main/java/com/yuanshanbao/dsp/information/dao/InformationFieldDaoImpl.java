package com.yuanshanbao.dsp.information.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.information.model.InformationField;

@Repository
public class InformationFieldDaoImpl extends BaseDaoImpl implements InformationFieldDao {
	@Override
	public List<InformationField> selectInformationFields(InformationField informationField, PageBounds pageBounds) {
		List<InformationField> list = getSqlSession().selectList("informationField.selectInformationField",
				informationField, pageBounds);
		return list;
	}

	@Override
	public int insertInformationField(InformationField informationField) {	
		return getSqlSession().insert("informationField.insertInformationField", informationField);
	}

	@Override
	public int deleteInformationField(Long informationFieldId) {
		return getSqlSession().delete("informationField.deleteInformationField", informationFieldId);
	}

	@Override
	public int updateInformationField(InformationField informationField) {
		return getSqlSession().update("informationField.updateInformationField", informationField);
	}

	@Override
	public List<InformationField> selectInformationFields(List<Long> informationFieldIdList) {
		if (informationFieldIdList == null || informationFieldIdList.size() == 0) {
			return new ArrayList<InformationField>();
		}
		return getSqlSession().selectList("informationField.selectInformationFieldByIds", informationFieldIdList);
	}

}
