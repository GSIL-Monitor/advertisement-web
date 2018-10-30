package com.yuanshanbao.dsp.material.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.material.model.Material;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MaterialDaoImpl extends BaseDaoImpl implements MaterialDao {
	@Override
	public List<Material> selectMaterials(Material material, PageBounds pageBounds) {
		return getSqlSession().selectList("material.selectMaterial", material, pageBounds);
	}

	@Override
	public int insertMaterial(Material material) {
		return getSqlSession().insert("material.insertMaterial", material);
	}

	@Override
	public int deleteMaterial(Material material) {
		return getSqlSession().delete("material.deleteMaterial", material);
	}

	@Override
	public int updateMaterial(Material material) {
		return getSqlSession().update("material.updateMaterial", material);
	}

	@Override
	public List<Material> selectMaterialsByIds(Map<String, Object> parameters, PageBounds pageBounds) {
		return getSqlSession().selectList("material.selectMaterialsByIds", parameters, pageBounds);
	}

}
