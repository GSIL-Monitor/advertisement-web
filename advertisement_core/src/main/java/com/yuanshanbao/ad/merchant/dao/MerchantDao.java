package com.yuanshanbao.ad.merchant.dao;

import java.util.List;

import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MerchantDao {

	public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds);

	public int insertMerchant(Merchant merchant);

	public int deleteMerchant(Long merchantId);

	public int updateMerchant(Merchant merchant);

	public List<Merchant> selectMerchants(List<Long> merchantIdList);
}
