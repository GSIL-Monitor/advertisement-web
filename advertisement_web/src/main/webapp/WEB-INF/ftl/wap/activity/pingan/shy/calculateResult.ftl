<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan3in1-wap.css"] />
</@htmlHead>
<#-- 头部信息（包括公司logo，菜单等） -->
<div class="calculate-header">
	<@commonHeader description=""/>
</div>
<div class="container calculate-background">
	<#-- 测保结果 -->
	<div class="policy-description">
		<@calculateResult/>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
		</div>
	</div>
	
	<#-- 测保说明 -->
	<@calculateDescription/>

	<#-- 尾部 -->
	<@footer activityPath="/pingan/shy"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<@commonFooterButton directDraw="true"/>
<@htmlFoot />