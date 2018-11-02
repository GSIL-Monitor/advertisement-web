package com.yuanshanbao.dsp.information.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.information.model.Information;

@Repository
public class InformationDaoImpl extends BaseDaoImpl implements InformationDao {
	@Override
	public List<Information> selectInformations(Information information, PageBounds pageBounds) {
		return getSqlSession().selectList("information.selectInformations", information, pageBounds);
	}

	@Override
	public int insertInformation(Information information) {
		return getSqlSession().insert("information.insertInformation", information);
	}

	@Override
	public int deleteInformation(Long informationId) {
		return getSqlSession().delete("information.deleteInformation", informationId);
	}

	@Override
	public int updateInformation(Information information) {
		return getSqlSession().update("information.updateInformation", information);
	}

	@Override
	public List<Information> selectInformations(List<Long> informationIdList) {
		if (informationIdList == null || informationIdList.size() == 0) {
			return new ArrayList<Information>();
		}
		return getSqlSession().selectList("information.selectInformationByIds", informationIdList);
	}

	@Override
	public Information selectinformationByMobile(String mobile) {
		 return getSqlSession().selectOne("information.selectinformationByMobile", mobile);
	}


}
