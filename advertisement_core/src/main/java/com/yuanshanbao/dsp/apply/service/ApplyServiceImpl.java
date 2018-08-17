package com.yuanshanbao.dsp.apply.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuanshanbao.common.exception.BusinessException;
import com.yuanshanbao.common.ret.ComRetCode;
import com.yuanshanbao.common.util.JacksonUtil;
import com.yuanshanbao.common.util.LoggerUtil;
import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.apply.dao.ApplyDao;
import com.yuanshanbao.dsp.apply.model.Apply;
import com.yuanshanbao.dsp.apply.model.ApplyStatus;
import com.yuanshanbao.dsp.common.constant.RedisConstant;
import com.yuanshanbao.dsp.common.redis.base.RedisService;
import com.yuanshanbao.dsp.core.CommonStatus;
import com.yuanshanbao.dsp.core.IniBean;
import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.service.InformationService;
import com.yuanshanbao.dsp.product.model.Product;
import com.yuanshanbao.dsp.product.model.vo.ProductVo;
import com.yuanshanbao.dsp.product.service.ProductService;
import com.yuanshanbao.dsp.quota.model.Quota;
import com.yuanshanbao.dsp.quota.service.QuotaService;
import com.yuanshanbao.dsp.user.model.User;
import com.yuanshanbao.paginator.domain.PageBounds;

@Service
public class ApplyServiceImpl implements ApplyService {

	private static final String INI_NAME_MAX = "apply_count_increase_max";

	private static final String INI_NAME_MIN = "apply_count_increase_min";

	private static final String INI_INITIAL_COUNT = "apply_count_initial";

	@Autowired
	private ProductService productService;

	@Autowired
	private InformationService informationService;

	@Autowired
	private QuotaService quotaService;

	@Autowired
	private ApplyDao applyDao;

	@Autowired
	private ApplyIdService applyIdService;

	@Autowired
	private RedisService redisCacheService;

	@Override
	public List<Apply> selectApplys(Apply apply, PageBounds pageBounds) {
		return setProperty(applyDao.selectApplys(apply, pageBounds));
	}

	private List<Apply> setProperty(List<Apply> list) {
		if (list.size() > 0) {
			for (Apply apply : list) {
				if (apply.getProductId() != null) {
					apply.setProduct(productService.selectProduct(apply.getProductId()));
				}
			}
		}
		return list;
	}

	@Override
	public Apply selectApply(Long applyId) {
		Apply apply = new Apply();
		if (applyId == null) {
			return null;
		}
		apply.setApplyId(applyId);
		List<Apply> list = selectApplys(apply, new PageBounds());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void insertApply(Apply apply) {
		int result = -1;

		result = applyDao.insertApply(apply);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void deleteApply(String applyId) {
		int result = -1;

		result = applyDao.deleteApply(applyId);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	@Override
	public void updateApply(Apply apply) {
		int result = -1;

		result = applyDao.updateApply(apply);

		if (result < 0) {
			throw new BusinessException(ComRetCode.FAIL);
		}
	}

	/**
	 * @param apply
	 * @return
	 * @throws IOException
	 */
	@Override
	public void insertOrUpdateApply(Apply apply) {
		if (apply.getApplyId() == null) {
			insertApply(apply);
		} else {
			updateApply(apply);
		}
	}

	@Override
	public Map<Long, Apply> selectApplyByIds(List<Long> applyIdList) {
		Map<Long, Apply> map = new HashMap<Long, Apply>();
		if (applyIdList == null || applyIdList.size() == 0) {
			return map;
		}
		List<Apply> list = applyDao.selectApplys(applyIdList);
		for (Apply apply : list) {
			map.put(apply.getApplyId(), apply);
		}
		return map;
	}

	@Override
	public void insertOrUpdateApply(User user, Long productId) {
		Product product = productService.selectProduct(productId);
		if (product == null) {
			throw new BusinessException(ComRetCode.WRONG_PARAMETER);
		}
		Apply apply = new Apply();
		apply.setMerchantId(product.getMerchantId());
		apply.setProductId(product.getProductId());
		apply.setUserId(user.getUserId());
		apply.setStatus(ApplyStatus.APPLIED);
		List<Apply> list = selectApplys(apply, new PageBounds());
		if (list.size() == 0) {
			insertApply(apply);
		} else {
			Apply app = list.get(0);
			apply.setApplyId(app.getApplyId());
			updateApply(apply);
		}
		increaseProductApplyCount(product);
	}

	@Override
	public Long getProductApplyCount(Long productId) {
		String key = RedisConstant.getProductApplyCountKey(productId);
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) IniBean.getIniIntegerValue(INI_INITIAL_COUNT + productId, 357625);
			redisCacheService.set(key, count + "");
		}
		return count;
	}

	private void increaseProductApplyCount(Product product) {
		int increaseCount = 1;
		String min = IniBean.getIniValue(INI_NAME_MIN, "1");
		String max = IniBean.getIniValue(INI_NAME_MAX, "9");
		double increaseMin = Double.parseDouble(min);
		double increaseMax = Double.parseDouble(max);
		increaseCount = (int) (Math.random() * (increaseMax - increaseMin + 1) + increaseMin);
		redisCacheService.increBy(RedisConstant.getProductApplyCountKey(product.getProductId()), increaseCount);
	}

	@Override
	public List<Apply> selectUserApplys(Apply apply, PageBounds pageBounds) {
		return setProperty(applyDao.selectUserApplys(apply, pageBounds));
	}

	@Override
	public Long getApplyCount() {
		String key = RedisConstant.getApplyShowCountKey();
		String str = (String) redisCacheService.get(key);
		long count = 0;
		if (ValidateUtil.isNumber(str)) {
			count = Long.parseLong(str);
		}
		if (count == 0) {
			count = (long) IniBean.getIniIntegerValue(INI_INITIAL_COUNT, 160213);
			redisCacheService.set(key, count + "");
		}
		return count;
	}

	@Override
	public void increaseApplyCount() {
		int increaseCount = 1;
		String min = IniBean.getIniValue(INI_NAME_MIN, "1");
		String max = IniBean.getIniValue(INI_NAME_MAX, "9");
		double increaseMin = Double.parseDouble(min);
		double increaseMax = Double.parseDouble(max);
		increaseCount = (int) (Math.random() * (increaseMax - increaseMin + 1) + increaseMin);
		redisCacheService.increBy(RedisConstant.getApplyShowCountKey(), increaseCount);
	}

	@Override
	public void applyProduct(User user, Long productId, Information information) {
		completeInformation(user, information);
		boolean applyExist = checkApplyExist(user, productId);
		if (applyExist) {
			throw new BusinessException(ComRetCode.APPLY_EXIST_ERROR);
		}
		if (productId != null) {
			checkInformationExist(user);
			Product product = productService.selectProduct(productId);
			if (product == null) {
				throw new BusinessException(ComRetCode.PRODUCT_NOT_EXIST_ERROR);
			}
			// 查找库存
			Quota quota = quotaService.pickProductForApply(user, product);
			if (quota == null) {
				throw new BusinessException(ComRetCode.ORDER_DELIVER_ERROR);
			}
			LoggerUtil.info("获取配额成功, 配合信息={}, 商品信息={}", JacksonUtil.obj2json(quota), JacksonUtil.obj2json(product));
			insertOrUpdateApply(user, productId);
		}
	}

	@Override
	public void checkExist(User user, ProductVo vo) {
		Apply apply = new Apply();
		apply.setUserId(user.getUserId());
		apply.setProductId(vo.getProductId());
		List<Apply> list = selectApplys(apply, new PageBounds());
		if (list.size() > 0) {
			vo.setStatus(ApplyStatus.APPLIED);
		} else {
			vo.setStatus(ApplyStatus.NOT);
		}
	}

	private void completeInformation(User user, Information information) {
		Information params = new Information();
		params.setUserId(user.getUserId());
		List<Information> list = informationService.selectInformations(params, new PageBounds());
		if (list.size() > 0) {
			information.setInformationId(list.get(0).getInformationId());
		}
		information.setUserId(user.getUserId());
		information.setStatus(CommonStatus.ONLINE);
		if (StringUtils.isNotEmpty(information.getName()) || information.getAge() != null) {
			informationService.insertOrUpdateInformation(information);
		}
	}

	private void checkInformationExist(User user) {
		Information params = new Information();
		params.setUserId(user.getUserId());
		List<Information> list = informationService.selectInformations(params, new PageBounds());
		if (list.size() < 0) {
			throw new BusinessException(ComRetCode.INFORMATION_NOT_COMPLETE);
		}
		Information information = list.get(0);
		if (information.getAge() == null || StringUtils.isEmpty(information.getName())) {
			throw new BusinessException(ComRetCode.INFORMATION_NOT_COMPLETE);
		}
	}

	public boolean checkApplyExist(User user, Long productId) {
		Apply apply = new Apply();
		apply.setUserId(user.getUserId());
		apply.setProductId(productId);
		apply.setStatus(ApplyStatus.APPLIED);
		List<Apply> list = selectApplys(apply, new PageBounds());
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
