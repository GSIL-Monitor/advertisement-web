package com.yuanshanbao.dsp.advertisement.model.click;

import com.yuanshanbao.dsp.advertisement.model.Advertisement;

public class AdvertisementPositionClick {

	private String position;
	private Advertisement advertisement;
	private String channel;
	private Long count;

	public AdvertisementPositionClick() {
		super();
	}

	public AdvertisementPositionClick(String position, Advertisement advertisement, String channel, long count) {
		this.position = position;
		this.advertisement = advertisement;
		this.channel = channel;
		this.count = count;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
