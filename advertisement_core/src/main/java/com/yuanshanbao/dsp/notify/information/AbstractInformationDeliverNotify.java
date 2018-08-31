package com.yuanshanbao.dsp.notify.information;

public abstract class AbstractInformationDeliverNotify implements InformationDeliverNotify {

	protected boolean forceDeliver;

	public void setForceDeliver() {
		this.forceDeliver = true;
	}

}
