<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan/zhxkj.css", "wap/base.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/calculate-banner.png?${cdnFileVersion}">
	</div>
	<div class="calculate-container">
		<@calculatePageTitle/>
		<@pinganHongYunCalculateForm activityPath="/pingan/zhxkj"/>
	</div>
	<#-- 测保说明 -->
	<@calculateDescription/>
	<#-- 尾部 -->
	<@footerBaiNian activityPath="/pingan/zhxkj"/>
</div>
<@htmlFoot />