<#include "../../../common/core.ftl" />
<@htmlHead title="《阳光真心守护保障计划》保费测算" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/yangguang/ktcx.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderYangGuang/>
	<#-- Banner信息 -->
	<div class="banner">
		<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/calculate-banner.png?${cdnFileVersion}">
	</div>

	<div class="form-area">
		<@calculatePageTitle/>
		<@yangguangZhenXinCalculateForm activityPath="/yg/ktcx"/>
	</div>
	
	<@calculateDescriptionYangGuang/>
	<#-- 尾部 -->
	<@footer activityPath="/yg/ktcx" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</div>
<@htmlFoot />