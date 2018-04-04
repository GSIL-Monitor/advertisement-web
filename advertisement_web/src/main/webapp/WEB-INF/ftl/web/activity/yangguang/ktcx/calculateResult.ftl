<#include "../../../common/core.ftl" />
<@htmlHead title="《阳光真心守护保障计划》保费测算结果" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/yangguang/calculate-web.css"] />
</@htmlHead>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeaderYangGuang/>
	<div class="result-wrap">
		<div class="policy-description">
			<@calculateResultYangGuang hasButton="true"/>

			<div class="policy-envelope">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
			</div>
		</div>
	</div>
		
	<#-- 测保说明 -->
	<@calculateDescriptionYangGuang/>
	<#-- 尾部 -->
	<@footer activityPath="/pingan/shy" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</div>
<@htmlFoot />