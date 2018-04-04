<#include "../../../common/core.ftl" />
<@htmlHead title="免费领取100万出行保障" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["wap/activity/pingan3in1.js", "plugins/mobiscroll.custom-2.17.0.min.js"] />
	<@cssFile file=["common/activity/shanyin/pinganwq.css", "plugins/mobiscroll.custom-2.17.0.min.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<#-- <div class="header">
		<img src="${cdnUrl}/img/common/logo/<#if channel??>/activity/pingan-logo-${channel}<#else>pingan-logo</#if>.png?${cdnFileVersion}">
	</div> -->

	<div class="banner">
		<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk">
			<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-banner.png">
		</a>
	</div>

	<div class="wrapper">
		<div class="home-description-item">
			<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk">
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-icon1.png">
			</a>
		</div>
		<div class="home-description-item">
			<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk">
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-icon2.png">
			</a>
		</div>
		<div class="home-description-item">
			<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk">
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-icon3.png">
			</a>
		</div>

		<div class="home-table-area">
			<div class="home-table-item">
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-description.png">
			</div>
			<div class="home-button">
				<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk"><img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-button.png"></a>
			</div>
						<div class="home-tip">
				<img src="${cdnUrl}/img/wap/activity/pingansy/wq/home-tip.png">
			</div>
		</div>
	</div>
</div>
<div class="submit-button-shadow">
	<a href="/m/activity/pingan/sywq/index.html?channel=shanyinsbk">
		<div class="home-link-button" id="indexButton">
			免费领取  100万出行保障GO>>
		</div>
	</a>
</div>
<@loading />
<@emailPopWindow />
<#if noIdentityCard?? && noIdentityCard=="true">
<div id="genderPopTipList" class="poptip">
	<ul>
	<div class="poptip-select-head" >请选择性别<span class="tip-close right"><img src="${cdnUrl}/img/wap/common/tip-close.png"></span></div>
		<#list genderList as gender>
			<li value="${gender.tagsId?c}">${gender.name}</li>
		</#list>
	</ul>
</div>
</#if>
<@htmlFoot/>