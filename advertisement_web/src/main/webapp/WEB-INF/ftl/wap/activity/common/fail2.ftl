<#include "../../common/core.ftl" />
<@htmlHead title="领取礼包" description="">
	<@jsFile file=["common/zengxian.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["wap/base.css","wap/activity/fail/fail.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container">
	<div class="wrapper">
		<#if insurant??>
		<div class="header"><img src="${cdnUrl}/img/wap/common/failpage/banner-invalid.png" alt=""></div>
		<div class="header-title">
			<h1>恭喜您，已成功提交申请</h1>
			<h2><span class="money">${insurance.fullName}</span></h2>
		</div>
		<#else>
		<div class="header"><img src="${cdnUrl}/img/wap/common/failpage/banner.png" alt=""></div>
		</#if>
		<div class="fuli"><img src="${cdnUrl}/img/wap/common/failpage/fuli.png" alt=""></div>
		<ul class="libao-list clearfix">
			<#list advertisementList as advertisement>
			<li>
				<a href="/m/j/common.html?position=${position}&id=${advertisement.advertisementId?c}">
					<img src="${advertisement.imageUrl}" alt="">
					<div class="cont-box">
						<#if advertisement.tags??>
						<img class="hot" src="${advertisement.tags.image}" alt="">
						</#if>
						<div class="inner">
							<div class="cont-head">${advertisement.title}</div>
							<div class="cont-body">${advertisement.subTitle}</div>
						</div>
					</div>
				</a>
			</li>
			</#list>
		</ul>
	</div>
</div>
<@htmlFoot/>
