<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["common/activity/yangguang/ktcx.css"] />
</@htmlHead>
<@notOriginalMerchantAlert/>
<div class="container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<#-- 保单详情 -->
	<div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/yangguang/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
			<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/table-header.png" class="table-header" />
			<@policyResultTable/>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/yangguang/ktcx/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
	<div class="policy-footer">
		<@footer activityPath="/yg/ktcx" chineseName="阳光人寿保险股份有限公司" englishName="Sunshine Insurance Group Inc."/>
	</div>
</div>
<#-- 尾部按钮 -->
<@commonFooterButton/>
<@htmlFoot />