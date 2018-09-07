package com.yuanshanbao.dsp.quota.service.operation;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

@Component
@Configurable
public class CPCOperationFactory implements QuotaOperationFactory {

	@Override
	public AdvertisementOperation getOperation() {
		return new CPMOperation();
	}
}
