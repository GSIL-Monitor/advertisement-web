package com.yuanshanbao.ad.article.model;

import java.sql.Timestamp;

import com.yuanshanbao.common.util.DateUtils;
import com.yuanshanbao.ad.core.CommonStatus;

public class ArticleSection {

	private Long sectionId;
	private Long articleId;
	private String title;
	private String content;
	private String imageUrl;
	private String description;
	private String buttonLabel;
	private Integer buttonType;
	private String buttonLink;
	private Integer contentType;
	private Integer sort;
	private Integer status;
	private Timestamp createTime;
	private Timestamp updateTime;

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

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

	public String getTypeValue() {
		return ArticleSectionStatus.getButtonDescription(buttonType);
	}

	public String getCreateTimeContent() {
		return DateUtils.format(createTime, "yyyy-MM-dd HH:mm:ss");
	}

	public String getStatusValue() {
		return CommonStatus.getDescription(status);
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getContentTypeValue() {
		return ArticleStatus.getContentTypeDescription(contentType);
	}

	public String getDescription() {
		return description;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
