<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/swzx/calculate-banner.png?${cdnFileVersion}">
	</div>

	<div class="form-area">
		<@calculatePageTitle/>
		<@pinganHongYunCalculateForm activityPath="/pingan/iphone"/>
	</div>
	<#-- 测保说明 -->
	<@calculateDescription/>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/iphone"/>
</div>
<@htmlFoot />