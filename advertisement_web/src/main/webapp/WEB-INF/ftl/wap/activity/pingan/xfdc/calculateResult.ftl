<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<div class="header">
		<img src="${cdnUrl}/img/common/logo/pingan-logo.png?${cdnFileVersion}">
	</div>
	<#-- 测保结果 -->
	<div class="policy-description">
		<@calculateResult/>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/swzx/policy-envelope.png">
		</div>
	</div>
	
	<#-- 测保说明 -->
	<@calculateDescription/>

	<#-- 尾部 -->
	<@footer activityPath="/pingan/ddhb"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<#-- <div class="footer-button">
	<#if giftPack?? && giftPack == "true">
		<div class="submit-button" id="giftButton">
			立即领取20元现金大礼包
		</div>
	<#else>
		<div class="submit-button" id="couponButton">
			立即领取滴滴红包
		</div>
	</#if>
</div> -->
<@commonFooterButton directDraw="true"/>
<@htmlFoot />