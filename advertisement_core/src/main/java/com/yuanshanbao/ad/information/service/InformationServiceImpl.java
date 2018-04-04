package com.yuanshanbao.ad.information.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.ad.channel.model.ChannelProcedure;
import com.yuanshanbao.ad.channel.service.ChannelProcedureService;
import com.yuanshanbao.ad.information.dao.InformationDao;
import com.yuanshanbao.ad.information.model.Information;
import com.yuanshanbao.ad.project.service.ProjectService;
import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class InformationServiceImpl implements InformationService {

	@Autowired
	private InformationDao informationDao;

	@Autowired
	private ChannelProcedureService informationProcedureService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ExtendInfoService extendInfoService;

	@Override
	public List<Information> selectInformations(Information information, PageBounds pageBounds) {
		return setProperties(informationDao.selectInformations(information, pageBounds));
	}

	private List<Information> setProperties(List<Information> informationList) {
		List<String> channelIdList = new ArrayList<String>();
		for (Information information : informationList) {
			channelIdList.add(information.getChannel());
		}
		Map<String, List<ChannelProcedure>> procedureMap = informationProcedureService
				.selectChannelProcedureByChannelIds(channelIdList);
		for (Information information : informationList) {
			information.setProcedureList(procedureMap.get(information.getChannel()));
		}
		return informationList;
	}

	@Override
	public Information selectInformation(Long informationId) {
		Information information = new Information();
		if (informationId == null) {
			return null;
		}
		information.setInformationId(informationId);
		List<Information> list = selectInformations(information, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertInformation(Information information) {
		int result = -1;

		result = informationDao.insertInformation(information);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteInformation(Long informationId) {
		int result = -1;

		result = informationDao.deleteInformation(informationId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateInformation(Information information) {
		int result = -1;

		result = informationDao.updateInformation(information);

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
	public void insertOrUpdateInformation(Information information) {
		if (information.getInformationId() == null) {
			insertInformation(information);
		} else {
			updateInformation(information);
		}
	}

	@Override
	public Map<Long, Information> selectInformationByIds(List<Long> informationIdList) {
		Map<Long, Information> map = new HashMap<Long, Information>();
		if (informationIdList == null || informationIdList.size() == 0) {
			return map;
		}
		List<Information> list = informationDao.selectInformations(informationIdList);
		for (Information information : list) {
			map.put(information.getInformationId(), information);
		}
		return map;
	}

	@Override
	public Information selectInformationByUserId(String userId) {
		Information information = new Information();
		information.setUserId(userId);
		List<Information> list = selectInformations(information, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
