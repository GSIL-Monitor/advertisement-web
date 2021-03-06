<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/pingan/ddhb.css"] />
</@htmlHead>
<@notOriginalMerchantAlert/>
<div class="container result-container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<#-- 保单详情 -->
	<div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
			<img src="${cdnUrl}/img/wap/activity/pingan/ddhb/table-header.png" class="table-header" />
			<@policyResultTable/>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/ddhb/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
</div>
<div class="policy-footer">
	<@footer activityPath="/pingan/ddhb"/>
</div>
<#if alert=="true">
	<#-- 尾部按钮 -->
	<div class="footer-button-placeholder"></div>
	<div class="footer-button">
		<div class="submit-button-shadow">
			<div class="submit-button" id="promotionButton">
				抽奖赢取iPhone 7 Plus
			</div>
		</div>
	</div>
</#if>
<@htmlFoot />