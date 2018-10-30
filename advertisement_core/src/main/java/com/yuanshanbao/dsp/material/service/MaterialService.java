package com.yuanshanbao.dsp.material.service;

import java.util.List;

import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MaterialService {

	public void insertMaterial(Material material);

	public void updateMaterial(Material material);

	public void deleteMaterial(Material material);

	public List<Material> selectMaterial(Material material, PageBounds pageBounds);

	public Material selectMaterial(Long materialId);

	public List<Long> selectMaterialIds(Material material);

	public List<Material> selectMaterialsByIds(Long advertiserId, String materialIds, Boolean isSelect,
			PageBounds pageBounds);

}
