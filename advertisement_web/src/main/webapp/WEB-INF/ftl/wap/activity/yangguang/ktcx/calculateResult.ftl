<#include "../../../common/core.ftl" />
<@htmlHead title="《阳光综合意外保障》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/yangguang/ktcx.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderYangGuang/>
	<#-- 测保结果 -->
	<div class="policy-description">
		<@calculateResultYangGuang/>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/policy-envelope.png">
		</div>
	</div>
	
	<@calculateDescriptionYangGuang/>

	<#-- 尾部 -->
	<@footer activityPath="/yg/ktcx" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<@commonFooterButton directDraw="true"/>
<@htmlFoot />