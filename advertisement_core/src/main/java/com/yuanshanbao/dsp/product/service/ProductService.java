package com.yuanshanbao.dsp.product.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.user.model.User;
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

	public Long getApplyCount(Long productId);

	public String getApplyInterface(Product product, User user, HttpServletRequest request);

	public List<Tags> getBrandFeatureMap(String brandFeature);

	public List<Product> selectProductByActivityId(Long activityId);

}
