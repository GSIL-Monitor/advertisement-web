<#include "../../common/core.ftl" />
<#include "../common/components.ftl" />
<#assign title="贷款产品">
<@htmlHead title="${title}" description="">
	<@jsFile file=["plugins/unslider.min.js", "plugins/jquery.event.move.js", "plugins/jquery.event.swipe.js", "wap/commonResult.js"] />
	<@cssFile file=["wap/base.css", "plugins/unslider.css", "wap/supermarket/supermarket.css", "wap/activity/common/common-result.css"] />
</@htmlHead>
<@calculatePage/>
<#if categoryList?? && (categoryList?size>0)>
	<section class="wrapper">
		<div class="banner">
			<ul>
				<#list bannerList as banner>
					<li>
						<a href="${banner.link}"><img src="${banner.imageUrl}" alt=""></a>
					</li>
				</#list>
			</ul>
		</div>
	</section>
</#if>
<section class="tab-box">
	<ul class="tabbar" id="tabUl">
		<#list categoryList as cate>
			<#if cate?? && (cate?length>0)>
			<li name="${cate.category.tagsId}" class="item <#if cate_index==0>act</#if>">${cate.category.name}
				<#if cate.category.image??>
					<span class="hot">
						<img src="${cate.category.image}">
					</span>
				</#if>
			</li>
			<span class="line"></span>
			</#if>
		</#list>
	</ul>
</section>
<section class="item-list" id="tabList">
	<#list categoryList as cate>
		<ul id="tabList${cate.category.tagsId}" class="<#if cate_index==0>show<#else>hide</#if> tab-list">
			<#list cate.advertisements as ad>
				<a href="${ad.link}">
					<li class="clearfix">
						<img class="logo left" src="${ad.imageUrl}" alt="">
						<div class="cont-box left">
							<h3 class="title">
								<span class="main-cont ellipsis">${ad.title}</span>
								<#if ad.tags.image ??>
								<span class="hot">
									<img src="${ad.tags.image}">
								</span>
								</#if>
							</h3>
							<p class="sub-cont ellipsis">${ad.subTitle}</p>
							<#if cate.category.description??>
							<p class="pers-num">
								<span class="count ellipsis">${ad.count}</span>
								<span>${cate.category.description}</span>
							</p>
							</#if>
						</div>
						<div class="a-btn ellipsis right">${ad.buttonName}</div>
					</li>
				</a>
			</#list>
		</ul>
	</#list>
</section>
<@htmlFoot/>