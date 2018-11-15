package com.yuanshanbao.dsp.material.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.advertiser.service.AdvertiserService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.material.dao.MaterialDao;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.paginator.domain.PageList;

@Repository
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	private MaterialDao materialDao;
	@Autowired
	private AdvertiserService advertiserService;

	@Override
	public void insertMaterial(Material material) {
		int result = -1;

		result = materialDao.insertMaterial(material);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateMaterial(Material material) {
		int result = -1;

		result = materialDao.updateMaterial(material);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public void deleteMaterial(Material material) {
		int result = -1;

		result = materialDao.deleteMaterial(material);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}

	}

	@Override
	public List<Material> selectMaterial(Material material, PageBounds pageBounds) {
		return setProperty(materialDao.selectMaterials(material, pageBounds));

	}

	@Override
	public List<Material> selectMaterialsByIds(Long advertiserId, String materialIds, Boolean isSelect,
			PageBounds pageBounds) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(materialIds)) {
			parameters.put("materialIdList", Arrays.asList(materialIds.split(",")));
		} else if (isSelect != null && isSelect) {
			return new PageList<Material>();
		}
		parameters.put("advertiserId", advertiserId);
		if (isSelect != null && isSelect) {
			parameters.put("isSelect", isSelect);
		}
		parameters.put("status", CommonStatus.ONLINE);
		return setProperty(materialDao.selectMaterialsByIds(parameters, pageBounds));

	}

	private List<Material> setProperty(List<Material> list) {
		List<Long> advertiserIds = new ArrayList<Long>();
		for (Material material : list) {
			advertiserIds.add(material.getAdvertiserId());
		}

		Map<Long, Advertiser> map = advertiserService.selectAdvertiserByIds(advertiserIds);
		for (Material material : list) {
			material.setAdvertiser(map.get(material.getAdvertiserId()));
		}
		return list;
	}

	@Override
	public Material selectMaterial(Long materialId) {
		List<Long> materialIds = new ArrayList<Long>();
		materialIds.add(materialId);
		Map<Long, Material> map = selectMaterialByIds(materialIds);
		return map.get(materialId);
	}

	@Override
	public List<Long> selectMaterialIds(Material material) {
		List<Long> resultList = new ArrayList<Long>();
		List<Material> list = selectMaterial(material, new PageBounds());
		for (Material parma : list) {
			resultList.add(parma.getMaterialId());
		}
		return resultList;
	}

	@Override
	public Map<Long, Material> selectMaterialByIds(List<Long> materialIds) {

		Map<Long, Material> map = new HashMap<Long, Material>();
		if (materialIds == null || materialIds.size() == 0) {
			return map;
		}
		List<Material> list = setProperty(materialDao.selectMaterialByIds(materialIds));

		for (Material param : list) {
			map.put(param.getMaterialId(), param);
		}
		return map;
	}
}
