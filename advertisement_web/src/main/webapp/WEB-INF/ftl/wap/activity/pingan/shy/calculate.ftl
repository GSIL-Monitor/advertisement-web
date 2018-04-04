<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan3in1-wap.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
<#-- 	<div class="header">
	</div> -->
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/shy/calculate-banner.jpg?${cdnFileVersion}">
	</div>
	<div class="calculate-container">
		<@calculatePageTitle/>
		<@pinganHongYunCalculateForm activityPath="/pingan/shy"/>
	</div>
	<#-- 测保说明 -->
	<@calculateDescription/>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/shy"/>
</div>
<@htmlFoot />