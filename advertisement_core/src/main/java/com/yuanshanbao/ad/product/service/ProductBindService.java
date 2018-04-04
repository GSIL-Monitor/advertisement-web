package com.yuanshanbao.ad.product.service;

import java.util.Map;
import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import org.springframework.stereotype.Service;
import com.yuanshanbao.ad.product.model.ProductBind;

/**
 * @description
 * @author
 */
@Service
public interface ProductBindService {

	public void insertProductBind(ProductBind productBind);

	public void updateProductBind(ProductBind productBind);

	public void deleteProductBind(ProductBind productBind);

	public List<ProductBind> selectProductBinds(ProductBind productBind, PageBounds pageBounds);
	
	public Map<Long, ProductBind> selectProductBindByIds(List<Long> bindIds);
	
	public void insertOrUpdateProductBind(ProductBind productBind);
}