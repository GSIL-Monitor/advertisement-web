<#include "../../../common/core.ftl" />
<@htmlHead title="领取成功" description="">
	<@jsFile file=["common/zengxian.js"] />
	<@cssFile file=["wap/base.css", "wap/activity/pingan/xxjhb.css"] />
</@htmlHead>
<@calculatePage/>
<div class="container result-container">
	<#-- 头部信息（包括公司logo，菜单等） -->
	<@commonHeader insurance="${merchant.englishName}" description=""/>
	<#-- 保单详情 -->
	<div class="policy-description">
		<img src="${cdnUrl}/img/wap/activity/pingan/common/policy-stamp.png" class="policy-stamp">
		<div class="policy-table">
			<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/table-header.png" class="table-header" />
			<@policyResultTable/>
		</div>
		<div class="policy-envelope">
			<img src="${cdnUrl}/img/wap/activity/pingan/zhxkj/policy-envelope.png">
		</div>
	</div>
	<@policyResultTip/>
	<input type="hidden" id="gorderId" value="${gorderId?c}" />
	<div class="policy-footer result-footer">
		<@footer activityPath="/pingan/xxjhb"/>
	</div>
</div>
<@notOriginalMerchantAlert/>
<#-- 尾部按钮 -->
<@commonFooterButton/>
<@htmlFoot />