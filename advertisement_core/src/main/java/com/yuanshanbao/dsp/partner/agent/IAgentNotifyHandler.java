package com.yuanshanbao.dsp.partner.agent;

import com.yuanshanbao.dsp.information.model.Information;

public interface IAgentNotifyHandler {

	// public void notifyAgent(Insurant insurant);

	public void notifyAgent(Information information);

	public void notifyAgent(String channel, String token);
}
