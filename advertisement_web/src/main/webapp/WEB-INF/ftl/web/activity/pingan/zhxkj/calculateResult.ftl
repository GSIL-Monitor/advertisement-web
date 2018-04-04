<#include "../../../common/core.ftl" />
<@htmlHead title="《平安鸿运随行意外保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/xfdc-web.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader/>
	<div class="result-wrap">
		<div class="policy-description">
			<@calculateResult hasButton="true"/>
			<div class="policy-envelope">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
			</div>
		</div>
	</div>
		
	<#-- 测保说明 -->
	<@calculateDescription/>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/zhxkj"/>
</div>
<@htmlFoot />