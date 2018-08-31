package com.yuanshanbao.dsp.notify.information;

import com.yuanshanbao.dsp.information.model.Information;

public interface InformationDeliverNotify {

	public boolean deliver(Information information);

	public void setForceDeliver();
}
