package com.yuanshanbao.dsp.material.dao;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MaterialDao {

	public List<Material> selectMaterials(Material creative, PageBounds pageBounds);

	public int insertMaterial(Material creative);

	public int deleteMaterial(Material creative);

	public int updateMaterial(Material creative);

	public List<Material> selectMaterialsByIds(Map<String, Object> parameters, PageBounds pageBounds);

}
