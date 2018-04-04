package com.yuanshanbao.ad.product.dao;


import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.ad.product.model.ProductBind;

/**
 * 
 * @description
 *
 * @author 
 */
public interface ProductBindDao {

	public int insertProductBind(ProductBind productBind);

	public int updateProductBind(ProductBind productBind);

	public int deleteProductBind(ProductBind productBind);

	public List<ProductBind> selectProductBinds(ProductBind productBind,
			PageBounds pageBounds);

	public List<ProductBind> selectProductBindByIds(List<Long> bindIds);
}