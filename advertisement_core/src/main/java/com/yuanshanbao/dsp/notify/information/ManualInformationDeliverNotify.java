package com.yuanshanbao.dsp.notify.information;

import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.model.InformationStatus;

public class ManualInformationDeliverNotify extends AbstractInformationDeliverNotify {

	@Override
	public boolean deliver(Information information) {
		information.setStatus(InformationStatus.SUBMIT_SUCCESS_STATE);
		return true;
	}

}
