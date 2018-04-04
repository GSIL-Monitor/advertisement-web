<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["web/activity/pingan/ddhb-web.css"] />
</@htmlHead>

<@notOriginalMerchantAlert/>

<div class="container result-container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<div class="result-wrap">
		<#-- 保单详情 -->
		<div class="policy-description">
			<div class="policy-stamp">
				<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png">
			</div>
			<div class="policy-table">
				<@policyResultTable/>
				<#-- 尾部按钮 -->
				<div class="submit-button-area">
					<@commonFooterButton/>
				</div>
			</div>
			<div class="policy-envelope">
				<img src="${cdnUrl}/img/wap/activity/pingan/shy/policy-envelope.png">
			</div>
			<@policyResultTip/>
		</div>
		<input type="hidden" id="gorderId" value="${gorderId?c}" />
	</div>
	<@footer activityPath="/yg/ktcx" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
</div>
<@htmlFoot />