package com.yuanshanbao.dsp.product.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ProductService {

	public List<Product> selectProducts(Product product, PageBounds pageBounds);

	public Product selectProduct(Long productId);

	public void insertProduct(Product product);

	public void deleteProduct(Long productId);

	public void updateProduct(Product product);

	public void insertOrUpdateProduct(Product product);

	public Map<Long, Product> selectProductByIds(List<Long> productIdList);

	public void increaseProductCount(Long productId);

	public Long getProductCount(Long productId);

}
