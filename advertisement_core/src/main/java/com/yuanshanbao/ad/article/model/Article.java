package com.yuanshanbao.ad.article.model;

import java.sql.Timestamp;
import java.util.List;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;

public class Article {

	private Long articleId;
	private String title;
	private String content;
	private String imageUrl;
	private String description;
	private String source;
	private String buttonLabel;
	private Integer buttonType;
	private String buttonLink;
	private Integer category;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	private String weixinUrl; // 微信分享图片
	private String weixinShareTitle; // 微信分享标题
	private String weixinShareDesc; // 微信分享描述

	private Integer contentType;
	private String linkValue;
	private List<ArticleSection> sectionList;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getContent() {
		if (contentType != null && contentType == 2) {
			content = content.replaceAll("<p", "<div");
			content = content.replaceAll("p>", "div>");
		}
		return content;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getButtonLabel() {
		return buttonLabel;
	}

	public void setButtonLabel(String buttonLabel) {
		this.buttonLabel = buttonLabel;
	}

	public Integer getButtonType() {
		return buttonType;
	}

	public void setButtonType(Integer buttonType) {
		this.buttonType = buttonType;
	}

	public String getButtonLink() {
		return buttonLink;
	}

	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<ArticleSection> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<ArticleSection> sectionList) {
		this.sectionList = sectionList;
	}

	public String getTypeValue() {
		return ArticleStatus.getButtonDescription(buttonType);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public String getLinkValue() {
		return linkValue;
	}

	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getWeixinUrl() {
		return weixinUrl;
	}

	public void setWeixinUrl(String weixinUrl) {
		this.weixinUrl = weixinUrl;
	}

	public String getWeixinShareTitle() {
		return weixinShareTitle;
	}

	public void setWeixinShareTitle(String weixinShareTitle) {
		this.weixinShareTitle = weixinShareTitle;
	}

	public String getWeixinShareDesc() {
		return weixinShareDesc;
	}

	public void setWeixinShareDesc(String weixinShareDesc) {
		this.weixinShareDesc = weixinShareDesc;
	}

	public String getDescription() {
		return description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getCategoryValue() {
		return ArticleStatus.getCategoryDescription(category);
	}

}
