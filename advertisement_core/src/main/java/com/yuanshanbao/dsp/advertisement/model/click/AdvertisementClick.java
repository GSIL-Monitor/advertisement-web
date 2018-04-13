package com.yuanshanbao.dsp.advertisement.model.click;

import java.util.ArrayList;
import java.util.List;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;

public class AdvertisementClick {
	private Advertisement advertisement;
	private String channel;
	private Long total;
	private List<AdvertisementPositionClick> positionClickList = new ArrayList<AdvertisementPositionClick>();

	public AdvertisementClick() {
		super();
	}

	public AdvertisementClick(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<AdvertisementPositionClick> getPositionClickList() {
		return positionClickList;
	}

	public void setPositionClickList(List<AdvertisementPositionClick> positionClickList) {
		this.positionClickList = positionClickList;
	}

	public void addPositionClick(AdvertisementPositionClick positionClick) {
		this.positionClickList.add(positionClick);
	}

}
