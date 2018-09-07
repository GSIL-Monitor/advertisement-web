package com.yuanshanbao.dsp.quota.service.operation;


public class CPMOperationFactory implements QuotaOperationFactory {

	@Override
	public AdvertisementOperation getOperation() {
		return new CPMOperation();
	}

}
