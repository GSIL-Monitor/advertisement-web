package com.yuanshanbao.dsp.partner.agent;

import com.yuanshanbao.dsp.advertiser.model.Advertiser;
import com.yuanshanbao.dsp.material.model.Material;

public interface IAgentDspHandler {
	public Advertiser creatAdvertiser(Advertiser advertiser);

	public void updateAdvertiser(Advertiser advertiser);

	public void checkAdvertisersStatus(Advertiser advertiser);

	public Material createMaterial(Material material, Advertiser advertiser);

	public void updateMaterial(Material material, Advertiser advertiser);

	public void checkMaterialStatus(Material material, Advertiser advertiser);
}
