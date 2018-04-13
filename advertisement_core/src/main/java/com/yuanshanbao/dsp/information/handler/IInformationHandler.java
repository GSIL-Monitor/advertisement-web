package com.yuanshanbao.dsp.information.handler;

import com.yuanshanbao.dsp.information.model.Information;
import com.yuanshanbao.dsp.information.model.InformationField;

public interface IInformationHandler {

	public void handleInformation(Information information, InformationField field);
}
