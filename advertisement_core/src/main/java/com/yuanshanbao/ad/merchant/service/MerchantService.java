package com.yuanshanbao.ad.merchant.service;

import java.util.List;
import java.util.Map;

import com.yuanshanbao.ad.merchant.model.Merchant;
import com.yuanshanbao.paginator.domain.PageBounds;

public interface MerchantService {

	public List<Merchant> selectMerchants(Merchant merchant, PageBounds pageBounds);

	public Merchant selectMerchant(Long merchantId);

	public void insertMerchant(Merchant merchant);

	public void deleteMerchant(Long merchantId);

	public void updateMerchant(Merchant merchant);

	public void insertOrUpdateMerchant(Merchant merchant);

	public Map<Long, Merchant> selectMerchantByIds(List<Long> merchantIdList);
	
	public List<Merchant> selectMerchantsWithInterface(Merchant merchant);

}
