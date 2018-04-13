package com.yuanshanbao.dsp.merchant.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yuanshanbao.dsp.base.dao.BaseDaoImpl;
import com.yuanshanbao.dsp.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;

@Repository
public class MerchantDaoImpl extends BaseDaoImpl implements MerchantDao {
	@Override
	public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds) {
		return getSqlSession().selectList("merchant.selectMerchant", merchant, pageBounds);
	}

	@Override
	public int insertMerchant(Merchant merchant) {
		return getSqlSession().insert("merchant.insertMerchant", merchant);
	}

	@Override
	public int deleteMerchant(Long merchantId) {
		return getSqlSession().delete("merchant.deleteMerchant", merchantId);
	}

	@Override
	public int updateMerchant(Merchant merchant) {
		return getSqlSession().update("merchant.updateMerchant", merchant);
	}

	@Override
	public List<Merchant> selectMerchants(List<Long> merchantIdList) {
		if (merchantIdList == null || merchantIdList.size() == 0) {
			return new ArrayList<Merchant>();
		}
		return getSqlSession().selectList("merchant.selectMerchantByIds", merchantIdList);
	}

}
