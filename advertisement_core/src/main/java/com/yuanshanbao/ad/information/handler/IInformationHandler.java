package com.yuanshanbao.ad.information.handler;

import com.yuanshanbao.ad.information.model.Information;
import com.yuanshanbao.ad.information.model.InformationField;

public interface IInformationHandler {

	public void handleInformation(Information information, InformationField field);
}
