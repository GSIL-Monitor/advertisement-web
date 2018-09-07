package com.yuanshanbao.dsp.quota.service.operation;

import org.springframework.stereotype.Component;

@Component
public class CPTOperationFactory implements QuotaOperationFactory {

	@Override
	public AdvertisementOperation getOperation() {
		return new CPTOperation();
	}
}
