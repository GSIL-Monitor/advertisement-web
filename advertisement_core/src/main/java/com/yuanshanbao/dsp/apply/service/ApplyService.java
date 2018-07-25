package com.yuanshanbao.dsp.apply.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface ApplyService {

	public List<Apply> selectApplys(Apply apply, PageBounds pageBounds);

	public Apply selectApply(String applyId);

	public void insertApply(Apply apply);

	public void deleteApply(String applyId);

	public void updateApply(Apply apply);

	public void insertOrUpdateApply(Apply apply);

	public Map<String, Apply> selectApplyByIds(List<String> applyIdList);

	public List<Apply> selectUserApplys(Apply apply, PageBounds pageBounds);

	public void insertOrUpdateApply(User user, Long productId, Integer applyStatus);

	public Long getApplyCount();

	public void increaseApplyCount();

	public void applyProduct(User user, Long productId, Information information, Integer status);

	public void checkExist(User user, ProductVo vo);

}
