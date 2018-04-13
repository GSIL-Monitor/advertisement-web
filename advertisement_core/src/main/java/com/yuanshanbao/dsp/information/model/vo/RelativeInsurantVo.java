package com.yuanshanbao.dsp.information.model.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yuanshanbao.common.util.ValidateUtil;
import com.yuanshanbao.dsp.common.constant.ConstantsManager;
import com.yuanshanbao.dsp.information.model.InformationRelative;
import com.yuanshanbao.dsp.tags.model.Tags;
import com.yuanshanbao.dsp.tags.model.vo.TagsVo;

public class RelativeInsurantVo {

	private String name;

	private List<TagsVo> infoTags;

	private List<TagsVo> recommendTags;

	public RelativeInsurantVo(InformationRelative relativeInsurant) {
		name = relativeInsurant.getName();
		infoTags = getInfoTags(relativeInsurant.getInfos());
		recommendTags = getRecommendTags(relativeInsurant.getRecommends());
	}

	public List<TagsVo> getInfoTags() {
		return infoTags;
	}

	public void setInfoTags(List<TagsVo> infoTags) {
		this.infoTags = infoTags;
	}

	public List<TagsVo> getRecommendTags() {
		return recommendTags;
	}

	public void setRecommendTags(List<TagsVo> recommendTags) {
		this.recommendTags = recommendTags;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<TagsVo> getRecommendTags(String recommends) {
		if (StringUtils.isBlank(recommends)) {
			return null;
		}
		String[] ids = recommends.split(",");
		if (ids == null || ids.length == 0) {
			return null;
		}
		List<TagsVo> result = new ArrayList<TagsVo>();
		for (String element : ids) {
			if (!ValidateUtil.isNumber(element)) {
				continue;
			}
			Tags exist = ConstantsManager.getTags(Long.valueOf(element));
			if (exist == null) {
				continue;
			}
			TagsVo vo = new TagsVo(exist);
			result.add(vo);
		}
		return result;
	}

	private List<TagsVo> getInfoTags(String infos) {
		if (StringUtils.isBlank(infos)) {
			return null;
		}
		String[] ids = infos.split(",");
		if (ids == null || ids.length == 0) {
			return null;
		}
		List<TagsVo> result = new ArrayList<TagsVo>();
		for (String element : ids) {
			if (!ValidateUtil.isNumber(element)) {
				continue;
			}
			Tags exist = ConstantsManager.getTags(Long.valueOf(element));
			if (exist == null) {
				continue;
			}
			TagsVo vo = new TagsVo(exist);
			result.add(vo);
		}
		return result;
	}

}
