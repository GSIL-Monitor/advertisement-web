package com.yuanshanbao.dsp.product.dao;

import java.util.List;

import com.yuanshanbao.paginator.domain.PageBounds;
import com.yuanshanbao.dsp.product.model.Product;

public interface ProductDao {

	public List<Product> selectProducts(Product product, PageBounds pageBounds);

	public int insertProduct(Product product);

	public int deleteProduct(Long productId);

	public int updateProduct(Product product);

	public List<Product> selectProducts(List<Long> productIdList);
}
