<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/activity/pingan/zhxkj.css", "wap/base.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container calculate-background">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<#-- 测保结果 -->
	<div class="policy-description">
		<@calculateResult/>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/policy-envelope.png">
		</div>
	</div>
	
	<#-- 测保说明 -->
	<@calculateDescription/>

	<#-- 尾部 -->
	<@footerZhongYing activityPath="/pingan/zhxkj"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<@commonFooterButton directDraw="true"/>
<@htmlFoot />