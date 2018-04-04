<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css"] />
</@htmlHead>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
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
	<@footer activityPath="/pingan/zhxkj"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<#if surveyPromotionTextConfig?? && (surveyPromotionTextConfig?length>0)>
<@commonFooterButton directDraw="true"/>
</#if>
<@htmlFoot />