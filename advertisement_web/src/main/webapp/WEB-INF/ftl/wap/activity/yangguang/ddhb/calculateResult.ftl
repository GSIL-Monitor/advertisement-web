<#include "../../../common/core.ftl" />
<@htmlHead title="《阳光真心守护保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/swzx.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderYangGuang/>
	<#-- 测保结果 -->
	<div class="policy-description">
		<@calculateResultYangGuang/>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/swzx/policy-envelope.png">
		</div>
	</div>
	
	<#-- 测保说明 -->
	<@calculateDescriptionYangGuang/>

	<#-- 尾部 -->
	<@footerYangGuang activityPath="/yg/ddhb"/>

	<div class="footer-button-placeholder"></div>
</div>

<#-- 尾部按钮 -->
<@commonFooterButton directDraw="true"/>
<@htmlFoot />